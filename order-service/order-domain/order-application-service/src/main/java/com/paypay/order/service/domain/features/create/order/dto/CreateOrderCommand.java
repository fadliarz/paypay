package com.paypay.order.service.domain.features.create.order.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateOrderCommand {

  private final UUID customerId;
  private final UUID storeId;
  private final List<OrderItemDto> items;
  private final BigDecimal totalPrice;
  private final String deliveryAddress;
}
