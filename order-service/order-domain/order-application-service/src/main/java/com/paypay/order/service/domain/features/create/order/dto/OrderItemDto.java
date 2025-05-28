package com.paypay.order.service.domain.features.create.order.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class OrderItemDto {

  private final ProductDto product;
  private final Integer quantity;
  private final BigDecimal subTotalPrice;
}
