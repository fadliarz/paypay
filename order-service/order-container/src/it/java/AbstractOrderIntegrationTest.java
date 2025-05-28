import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.TestcontainersExtension;
import org.testcontainers.utility.DockerImageName;

@ExtendWith(TestcontainersExtension.class)
@Slf4j
public class AbstractOrderIntegrationTest {

  protected static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER;
  protected static DataSource DATA_SOURCE;

  static {
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException classNotFoundException) {
      throw new RuntimeException("Critical: PostgreSQL driver not found", classNotFoundException);
    }

    POSTGRE_SQL_CONTAINER =
        new PostgreSQLContainer<>(DockerImageName.parse("postgres:15.2-alpine"))
            .withDatabaseName("order")
            .withUsername("postgres")
            .withPassword("password")
            .withCommand("postgres", "-c", "log_statement=all");
    POSTGRE_SQL_CONTAINER.start();
    log.info("PostgreSQL container started at {}", POSTGRE_SQL_CONTAINER.getJdbcUrl());

    try (Connection c =
        java.sql.DriverManager.getConnection(
            POSTGRE_SQL_CONTAINER.getJdbcUrl(),
            POSTGRE_SQL_CONTAINER.getUsername(),
            POSTGRE_SQL_CONTAINER.getPassword())) {
    } catch (SQLException sqlException) {
      throw new RuntimeException(
          "Critical: Could not connect to Testcontainer directly vid DriverManager", sqlException);
    }

    initializeDataSource();
    applyMigrations();
  }

  private static void initializeDataSource() {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setJdbcUrl(POSTGRE_SQL_CONTAINER.getJdbcUrl());
    hikariConfig.setSchema("order");
    hikariConfig.setUsername(POSTGRE_SQL_CONTAINER.getUsername());
    hikariConfig.setPassword(POSTGRE_SQL_CONTAINER.getPassword());
    hikariConfig.setDriverClassName(POSTGRE_SQL_CONTAINER.getDriverClassName());
    hikariConfig.setMaximumPoolSize(5);
    DATA_SOURCE = new HikariDataSource(hikariConfig);
    System.out.println("Hikari DataSource initialized.");
  }

  private static void applyMigrations() {
    Flyway flyway =
        Flyway.configure()
            .dataSource(
                POSTGRE_SQL_CONTAINER.getJdbcUrl(),
                POSTGRE_SQL_CONTAINER.getUsername(),
                POSTGRE_SQL_CONTAINER.getPassword())
            .locations("classpath:migration")
            .schemas("order")
            .load();
    flyway.migrate();
    log.info("Flyway migrations applied successfully");
  }

  @DynamicPropertySource
  private static void registerPgProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
    log.info("Registering postgres properties...");
    dynamicPropertyRegistry.add("spring.datasource.url", POSTGRE_SQL_CONTAINER::getJdbcUrl);
    dynamicPropertyRegistry.add("spring.datasource.username", POSTGRE_SQL_CONTAINER::getUsername);
    dynamicPropertyRegistry.add("spring.datasource.password", POSTGRE_SQL_CONTAINER::getPassword);
    dynamicPropertyRegistry.add("spring.jpa.hibernate.hbm2ddl.auto", () -> "validate");
    log.info("Registered postgres properties");
  }

  @BeforeEach
  void cleanTablesBetweenTests() {
    String[] tables = {"orders", "order_items"};
    try (Connection connection = DATA_SOURCE.getConnection()) {
      for (String table : tables) {
        try (PreparedStatement preparedStatement =
            connection.prepareStatement("TRUNCATE TABLE " + table + " RESTART IDENTITY CASCADE")) {
          preparedStatement.executeUpdate();
          log.info("Test Prep: table {} truncated", table);
        }
      }
    } catch (SQLException sqlException) {
      throw new RuntimeException("Failed to clean tables!", sqlException);
    }
  }
}
