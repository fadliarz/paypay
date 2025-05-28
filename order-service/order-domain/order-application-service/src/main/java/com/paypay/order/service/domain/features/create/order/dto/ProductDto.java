package com.paypay.order.service.domain.features.create.order.dto;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProductDto {

  private final UUID id;
  private final String image;
  private final String name;
  private final String description;
  private final BigDecimal price;
}
