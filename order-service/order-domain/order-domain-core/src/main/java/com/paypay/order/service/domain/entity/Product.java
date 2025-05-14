package com.paypay.order.service.domain.entity;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {

  private UUID productId;
  private String image;
  private String name;
  private String description;
  private BigDecimal price;
}
