package com.paypay.order.service.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.paypay.order.service.domain.entity.Order;
import com.paypay.order.service.domain.exception.CustomerNotFoundException;
import com.paypay.order.service.domain.exception.StoreNotFoundException;
import com.paypay.order.service.domain.features.create.order.CreateOrderCommandHandler;
import com.paypay.order.service.domain.ports.output.repository.OrderRepository;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class CreateOrderIntegrationTest extends AbstractOrderIntegrationTest {

  @Autowired private CreateOrderCommandHandler createOrderCommandHandler;
  @Autowired private OrderRepository orderRepository;

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
  }

  @Test
  void placeOrder_whenTotalPriceIsInvalid_throwError() {
    mockFindStoreWithProducts200Ok();
    createOrderCommandBuilder.totalPrice(createOrderCommand.getTotalPrice().add(BigDecimal.ONE));

    assertThrows(
        RuntimeException.class,
        () -> createOrderCommandHandler.handle(createOrderCommandBuilder.build()));
  }

  @Test
  void placeOrder_whenStoreNotFound_throwStoreNotFoundException() {
    mockFindStoreWithProduct404NotFound();

    assertNotNull(createOrderCommand);
    assertThrows(
        StoreNotFoundException.class, () -> createOrderCommandHandler.handle(createOrderCommand));
  }

  @Test
  void placeOrder_whenCustomerNotFound__throwCustomerNotFoundException() {
    createOrderCommandBuilder.customerId(UUID.randomUUID());

    assertThrows(
        CustomerNotFoundException.class,
        () -> createOrderCommandHandler.handle(createOrderCommandBuilder.build()));
  }
}
