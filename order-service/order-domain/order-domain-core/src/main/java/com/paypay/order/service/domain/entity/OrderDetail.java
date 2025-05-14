package com.paypay.order.service.domain.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class OrderDetail {

  private UUID orderDetailId;
  private UUID storeId;
  private List<OrderItem> orderItems;
  private BigDecimal price;

  private OrderDetail(Builder builder) {
    orderDetailId = builder.orderDetailId;
    storeId = builder.storeId;
    orderItems = builder.orderItems;
    price = builder.price;
  }

  public UUID getOrderDetailId() {
    return orderDetailId;
  }

  public UUID getStoreId() {
    return storeId;
  }

  public List<OrderItem> getOrderItems() {
    return orderItems;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public static final class Builder {

    private UUID orderDetailId;
    private UUID storeId;
    private List<OrderItem> orderItems;
    private BigDecimal price;

    private Builder() {}

    public static Builder builder() {
      return new Builder();
    }

    public Builder orderDetailId(UUID val) {
      orderDetailId = val;
      return this;
    }

    public Builder storeId(UUID val) {
      storeId = val;
      return this;
    }

    public Builder orderItems(List<OrderItem> val) {
      orderItems = val;
      return this;
    }

    public Builder price(BigDecimal val) {
      price = val;
      return this;
    }

    public OrderDetail build() {
      return new OrderDetail(this);
    }
  }
}
