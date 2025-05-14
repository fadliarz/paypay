package com.paypay.order.service.domain.entity;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderItem {

  private UUID orderItemId;
  private Product product;
  private Integer quantity;
  private BigDecimal price;
}
