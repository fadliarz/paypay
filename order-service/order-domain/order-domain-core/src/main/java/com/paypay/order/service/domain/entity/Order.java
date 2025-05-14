package com.paypay.order.service.domain.entity;

import java.util.List;
import java.util.UUID;

public class Order {

  private UUID orderId;
  private UUID userId;
  private String deliveryAddress;
  private List<OrderDetail> orderDetails;
}
