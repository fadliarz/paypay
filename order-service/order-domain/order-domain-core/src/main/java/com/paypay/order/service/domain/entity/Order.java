package com.paypay.order.service.domain.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class Order {

  private final UUID customerId;
  private final UUID orderId;
  private final UUID storeId;
  private final String deliveryAddress;
  private final List<OrderItem> orderItems;
  private final BigDecimal price;

  private Order(Builder builder) {
    customerId = builder.customerId;
    orderId = builder.orderId;
    storeId = builder.storeId;
    deliveryAddress = builder.deliveryAddress;
    orderItems = builder.orderItems;
    price = builder.price;
  }

  public UUID getCustomerId() {
    return customerId;
  }

  public UUID getOrderId() {
    return orderId;
  }

  public UUID getStoreId() {
    return storeId;
  }

  public String getDeliveryAddress() {
    return deliveryAddress;
  }

  public List<OrderItem> getOrderItems() {
    return orderItems;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public static final class Builder {

    private UUID customerId;
    private UUID orderId;
    private UUID storeId;
    private String deliveryAddress;
    private List<OrderItem> orderItems;
    private BigDecimal price;

    private Builder() {}

    public static Builder builder() {
      return new Builder();
    }

    public Builder setCustomerId(UUID val) {
      customerId = val;
      return this;
    }

    public Builder setOrderId(UUID val) {
      orderId = val;
      return this;
    }

    public Builder setStoreId(UUID val) {
      storeId = val;
      return this;
    }

    public Builder setDeliveryAddress(String val) {
      deliveryAddress = val;
      return this;
    }

    public Builder setOrderItems(List<OrderItem> val) {
      orderItems = val;
      return this;
    }

    public Builder setPrice(BigDecimal val) {
      price = val;
      return this;
    }

    public Order build() {
      return new Order(this);
    }
  }
}
