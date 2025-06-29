package com.paypay.order.service.domain;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.paypay.saga.order.SagaConstants.ORDER_SAGA_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.paypay.order.service.client.store.entity.ProductDetails;
import com.paypay.order.service.client.store.entity.StoreDetails;
import com.paypay.order.service.domain.features.create.order.CreateOrderCommandHandler;
import com.paypay.order.service.domain.features.create.order.dto.CreateOrderCommand;
import com.paypay.order.service.domain.features.create.order.dto.OrderItemDto;
import com.paypay.order.service.domain.mapper.OrderIntegrationTestDataMapper;
import com.paypay.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage;
import com.paypay.order.service.domain.ports.output.repository.OrderRepository;
import com.paypay.order.service.domain.ports.output.repository.PaymentOutboxRepository;
import com.paypay.outbox.OutboxStatus;
import com.paypay.saga.SagaStatus;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.TestcontainersExtension;
import org.testcontainers.utility.DockerImageName;

@Slf4j
@SpringBootTest(
    classes = {
      OrderServiceApplication.class,
      AbstractOrderIntegrationTest.DataSourceTestConfiguration.class,
    })
@ExtendWith(TestcontainersExtension.class)
public class AbstractOrderIntegrationTest {

  @Autowired protected CreateOrderCommandHandler createOrderCommandHandler;
  @Autowired protected OrderRepository orderRepository;
  @Autowired protected PaymentOutboxRepository paymentOutboxRepository;
  @Autowired protected static DataSource dataSource;

  protected static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER;
  protected static final String ORDER_SCHEMA = "order";

  protected static final ObjectMapper objectMapper = new ObjectMapper();
  protected static WireMockServer wireMockServer;

  protected static CreateOrderCommand createOrderCommand;
  protected static CreateOrderCommand.CreateOrderCommandBuilder createOrderCommandBuilder;
  protected static UUID customerId;
  protected static UUID mockedStoreId;
  protected static StoreDetails mockedStoreDetails;
  protected static List<ProductDetails> mockedProducts;
  protected static ProductDetails mockedFirstProductDetails;
  protected static ProductDetails mockedSecondProductDetails;

  static class DataSourceTestConfiguration {
    @Bean
    @Primary
    public DataSource springManagedDataSource() {
      return AbstractOrderIntegrationTest.dataSource;
    }
  }

  static {
    objectMapper.registerModule(new JavaTimeModule());

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
    hikariConfig.addDataSourceProperty("binaryTransfer", true);
    hikariConfig.addDataSourceProperty("stringtype", "unspecified");
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
    dynamicPropertyRegistry.add(
        "spring.datasource.url",
        () ->
            String.format(
                "%s/?currentSchema=%s&stringtype=unspecified",
                POSTGRE_SQL_CONTAINER.getJdbcUrl(), ORDER_SCHEMA));
    dynamicPropertyRegistry.add("spring.datasource.username", POSTGRE_SQL_CONTAINER::getUsername);
    dynamicPropertyRegistry.add("spring.datasource.password", POSTGRE_SQL_CONTAINER::getPassword);
    dynamicPropertyRegistry.add("spring.jpa.hibernate.hbm2ddl.auto", () -> "validate");
    log.info("Registered postgres properties");
  }

  @BeforeEach
  public void setupMockedObject() {
    customerId = UUID.fromString("f87e1fad-0a7e-49f5-abcf-bfdad2fff7d2");
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

    createOrderCommandBuilder = CreateOrderCommand.builder();
    createOrderCommandBuilder.storeId(mockedStoreId);
    createOrderCommandBuilder.customerId(customerId);
    List<OrderItemDto> orderItemDtoList =
        OrderIntegrationTestDataMapper.productDetailsListToOrderItemDtoList(mockedProducts);
    createOrderCommandBuilder.items(orderItemDtoList);
    createOrderCommandBuilder.totalPrice(
        orderItemDtoList.stream()
            .map(OrderItemDto::getSubTotalPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add));
    createOrderCommandBuilder.deliveryAddress("Bandung, West Java");
    createOrderCommand = createOrderCommandBuilder.build();
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

  protected void assertEmptyOrderPaymentOutboxMessage() {
    List<OrderPaymentOutboxMessage> orderPaymentOutboxMessageList =
        paymentOutboxRepository.findByTypeAndOutboxStatusAndSagaStatusIn(
            ORDER_SAGA_NAME, OutboxStatus.STARTED, SagaStatus.STARTED);
    assertEquals(0, orderPaymentOutboxMessageList.size());
  }
}
