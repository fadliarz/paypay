package com.paypay.order.service.domain.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class OrderDetail {

  private UUID orderDetailId;
  private UUID storeId;
  private List<OrderItem> orderItems;
  private BigDecimal price;
}
