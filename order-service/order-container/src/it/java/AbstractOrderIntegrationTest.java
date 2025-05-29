import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.paypay.order.service.client.store.entity.ProductDetails;
import com.paypay.order.service.client.store.entity.StoreDetails;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.TestcontainersExtension;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.utility.DockerImageName;

@ExtendWith(TestcontainersExtension.class)
@Slf4j
public class AbstractOrderIntegrationTest {
  protected static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER;
  protected static final String ORDER_SCHEMA = "order";

  protected static final ObjectMapper objectMapper = new ObjectMapper();
  protected static WireMockServer wireMockServer;
  protected static DataSource dataSource;

  protected static UUID mockedStoreId;
  protected static StoreDetails mockedStoreDetails;
  protected static List<ProductDetails> mockedProducts;
  protected static ProductDetails mockedFirstProductDetails;
  protected static ProductDetails mockedSecondProductDetails;

  static {
    wireMockServer = new WireMockServer(9121);

    POSTGRE_SQL_CONTAINER =
        new PostgreSQLContainer<>(DockerImageName.parse("postgres:15.2-alpine"))
            .withDatabaseName("order")
            .withUsername("postgres")
            .withPassword("password")
            .withCommand("postgres", "-c", "log_statement=all");
    POSTGRE_SQL_CONTAINER.start();
    log.info("PostgreSQL container started at {}", POSTGRE_SQL_CONTAINER.getJdbcUrl());

    initializeDataSource();
    applyMigrations();
  }

  protected static void initializeDataSource() {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setJdbcUrl(POSTGRE_SQL_CONTAINER.getJdbcUrl());
    hikariConfig.addDataSourceProperty("currentSchema", ORDER_SCHEMA);
    hikariConfig.setUsername(POSTGRE_SQL_CONTAINER.getUsername());
    hikariConfig.setPassword(POSTGRE_SQL_CONTAINER.getPassword());
    hikariConfig.setDriverClassName(POSTGRE_SQL_CONTAINER.getDriverClassName());
    hikariConfig.setMaximumPoolSize(5);
    dataSource = new HikariDataSource(hikariConfig);
    System.out.println("Hikari DataSource initialized.");
  }

  protected static void applyMigrations() {
    Flyway flyway =
        Flyway.configure()
            .dataSource(dataSource)
            .locations("classpath:migration")
            .schemas(ORDER_SCHEMA)
            .load();
    flyway.migrate();
    log.info("Flyway migrations applied successfully");
  }

  @DynamicPropertySource
  protected static void registerPgProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
    log.info("Registering postgres properties...");
    dynamicPropertyRegistry.add("spring.datasource.url", POSTGRE_SQL_CONTAINER::getJdbcUrl);
    dynamicPropertyRegistry.add("spring.datasource.username", POSTGRE_SQL_CONTAINER::getUsername);
    dynamicPropertyRegistry.add("spring.datasource.password", POSTGRE_SQL_CONTAINER::getPassword);
    dynamicPropertyRegistry.add("spring.jpa.hibernate.hbm2ddl.auto", () -> "validate");
    log.info("Registered postgres properties");
  }

  @BeforeAll
  public static void setupMockedObject() {
    mockedStoreId = UUID.randomUUID();
    mockedFirstProductDetails =
        ProductDetails.builder()
            .id(UUID.randomUUID())
            .image("image")
            .name("name")
            .description("description")
            .price(BigDecimal.valueOf(5))
            .build();
    mockedSecondProductDetails =
        ProductDetails.builder()
            .id(UUID.randomUUID())
            .image("image")
            .name("name")
            .description("description")
            .price(BigDecimal.valueOf(5))
            .build();
    mockedProducts = List.of(mockedFirstProductDetails, mockedSecondProductDetails);
    mockedStoreDetails = StoreDetails.builder().id(mockedStoreId).products(mockedProducts).build();
  }

  @BeforeEach
  void startWireMockServer() {
    wireMockServer.start();
    log.info("Started WireMock server");
  }

  @BeforeEach
  void cleanTablesBetweenTests() {
    String[] tables = {"orders", "order_items"};
    try (Connection connection = dataSource.getConnection()) {
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

  @AfterEach
  void stopWireMockServer() {
    wireMockServer.stop();
    log.info("Stopped WireMock server");
  }

  protected void mockFindStoreWithProducts200Ok() {
    try {
      wireMockServer.stubFor(
          get(urlPathEqualTo("/stores/" + mockedStoreId + "/products"))
              .willReturn(
                  aResponse()
                      .withStatus(200)
                      .withHeader("Content-Type", "application/json")
                      .withBody(objectMapper.writeValueAsString(mockedStoreDetails))));
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  protected void mockFindStoreWithProduct404NotFound() {
    wireMockServer.stubFor(
        get(urlPathEqualTo("/stores/" + mockedStoreId + "/products"))
            .willReturn(
                aResponse().withStatus(404).withHeader("Content-Type", "application/json")));
  }
}
