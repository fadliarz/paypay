package com.paypay.order.service.domain.entity;

import java.util.List;
import java.util.UUID;

public class Order {

  private UUID orderId;
  private UUID userId;
  private String deliveryAddress;
  private List<OrderDetail> orderDetails;

  private Order(Builder builder) {
    orderId = builder.orderId;
    userId = builder.userId;
    deliveryAddress = builder.deliveryAddress;
    orderDetails = builder.orderDetails;
  }

  public UUID getOrderId() {
    return orderId;
  }

  public UUID getUserId() {
    return userId;
  }

  public String getDeliveryAddress() {
    return deliveryAddress;
  }

  public List<OrderDetail> getOrderDetails() {
    return orderDetails;
  }

  public static final class Builder {

    private UUID orderId;
    private UUID userId;
    private String deliveryAddress;
    private List<OrderDetail> orderDetails;

    private Builder() {}

    public static Builder builder() {
      return new Builder();
    }

    public Builder orderId(UUID val) {
      orderId = val;
      return this;
    }

    public Builder userId(UUID val) {
      userId = val;
      return this;
    }

    public Builder deliveryAddress(String val) {
      deliveryAddress = val;
      return this;
    }

    public Builder orderDetails(List<OrderDetail> val) {
      orderDetails = val;
      return this;
    }

    public Order build() {
      return new Order(this);
    }
  }
}
