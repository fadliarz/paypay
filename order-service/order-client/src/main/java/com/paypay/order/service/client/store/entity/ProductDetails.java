package com.paypay.order.service.client.store.entity;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDetails {

  private UUID id;
  private String image;
  private String name;
  private String description;
  private BigDecimal price;
}
