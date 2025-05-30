package com.paypay.order.service.domain.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.paypay.order.service.domain.features.create.order.dto.CreateOrderCommand;
import com.paypay.order.service.domain.features.create.order.dto.OrderItemDto;
import com.paypay.order.service.domain.features.create.order.dto.ProductDto;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class CommandFactory {

  public static CreateOrderCommand newCreateOrderCommand() {
    UUID customerId = UUID.randomUUID();
    UUID storeId = UUID.randomUUID();
    String deliveryAddress = "West Java, Indonesia";
    ProductDto product1 =
        ProductDto.builder()
            .id(UUID.randomUUID())
            .image("mockedImage")
            .name("mockedName")
            .description("mockedDescription")
            .price(BigDecimal.valueOf(1))
            .build();
    ProductDto product2 =
        ProductDto.builder()
            .id(UUID.randomUUID())
            .image("mockedImage")
            .name("mockedName")
            .description("mockedDescription")
            .price(BigDecimal.valueOf(1))
            .build();
    OrderItemDto orderItem1 =
        OrderItemDto.builder()
            .product(product1)
            .quantity(1)
            .subTotalPrice(BigDecimal.valueOf(1))
            .build();
    OrderItemDto orderItem2 =
        OrderItemDto.builder()
            .product(product2)
            .quantity(1)
            .subTotalPrice(BigDecimal.valueOf(1))
            .build();
    List<OrderItemDto> orderItems = List.of(orderItem1, orderItem2);
    CreateOrderCommand createOrderCommand =
        CreateOrderCommand.builder()
            .customerId(customerId)
            .storeId(storeId)
            .deliveryAddress(deliveryAddress)
            .items(orderItems)
            .totalPrice(BigDecimal.valueOf(2))
            .build();

    assertEquals(
        orderItem1.getSubTotalPrice(),
        product1.getPrice().multiply(BigDecimal.valueOf(orderItem1.getQuantity())),
        "value mismatch on: orderItem1:subTotalPrice");
    assertEquals(
        orderItem2.getSubTotalPrice(),
        product2.getPrice().multiply(BigDecimal.valueOf(orderItem2.getQuantity())),
        "value mismatch on: orderItem2:subTotalPrice");
    assertEquals(
        orderItem1.getSubTotalPrice().add(orderItem2.getSubTotalPrice()),
        createOrderCommand.getTotalPrice(),
        "value mismatch on: createOrderCommand:totalPrice");

    return createOrderCommand;
  }
}
