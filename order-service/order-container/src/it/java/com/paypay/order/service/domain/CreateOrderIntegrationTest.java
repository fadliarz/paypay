package com.paypay.order.service.domain;

import static com.paypay.saga.order.SagaConstants.ORDER_SAGA_NAME;
import static org.junit.jupiter.api.Assertions.*;

import com.paypay.order.service.domain.entity.Order;
import com.paypay.order.service.domain.exception.CustomerNotFoundException;
import com.paypay.order.service.domain.exception.StoreNotFoundException;
import com.paypay.order.service.domain.outbox.model.payment.OrderPaymentEventPayload;
import com.paypay.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage;
import com.paypay.outbox.OutboxStatus;
import com.paypay.saga.SagaStatus;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class CreateOrderIntegrationTest extends AbstractOrderIntegrationTest {

  @Test
  void placeOrder_whenCommandIsValid_persistOrderAndItems() {
    mockFindStoreWithProducts200Ok();

    Order orderResult = createOrderCommandHandler.handle(createOrderCommand).getOrder();

    assertNotNull(orderResult);
    assertNotNull(orderResult.getId());
    Optional<Order> optionalSavedOrder = orderRepository.findOrder(orderResult.getId());
    assertFalse(optionalSavedOrder.isEmpty());
    Order savedOrder = optionalSavedOrder.get();
    assertNotNull(savedOrder);
    assertEquals(savedOrder, savedOrder);
    assertNotNull(orderResult);

    List<OrderPaymentOutboxMessage> orderPaymentOutboxMessageList =
        paymentOutboxRepository.findByTypeAndOutboxStatusAndSagaStatusIn(
            ORDER_SAGA_NAME, OutboxStatus.STARTED, SagaStatus.STARTED);
    assertEquals(1, orderPaymentOutboxMessageList.size());
    OrderPaymentEventPayload orderPaymentEventPayload;
    try {
      orderPaymentEventPayload =
          objectMapper.readValue(
              orderPaymentOutboxMessageList.get(0).getPayload(), OrderPaymentEventPayload.class);
    } catch (IOException e) {
      throw new RuntimeException("Failed to deserialize OrderPaymentEventPayload");
    }
    assertEquals(orderResult.getId(), UUID.fromString(orderPaymentEventPayload.getOrderId()));
  }

  @Test
  void placeOrder_whenTotalPriceIsInvalid_throwError() {
    mockFindStoreWithProducts200Ok();
    createOrderCommandBuilder.totalPrice(createOrderCommand.getTotalPrice().add(BigDecimal.ONE));

    assertThrows(
        RuntimeException.class,
        () -> createOrderCommandHandler.handle(createOrderCommandBuilder.build()));
    assertEmptyOrderPaymentOutboxMessage();
  }

  @Test
  void placeOrder_whenStoreNotFound_throwStoreNotFoundException() {
    mockFindStoreWithProduct404NotFound();

    assertNotNull(createOrderCommand);
    assertThrows(
        StoreNotFoundException.class, () -> createOrderCommandHandler.handle(createOrderCommand));
    assertEmptyOrderPaymentOutboxMessage();
  }

  @Test
  void placeOrder_whenCustomerNotFound__throwCustomerNotFoundException() {
    createOrderCommandBuilder.customerId(UUID.randomUUID());

    assertThrows(
        CustomerNotFoundException.class,
        () -> createOrderCommandHandler.handle(createOrderCommandBuilder.build()));
    assertEmptyOrderPaymentOutboxMessage();
  }
}
