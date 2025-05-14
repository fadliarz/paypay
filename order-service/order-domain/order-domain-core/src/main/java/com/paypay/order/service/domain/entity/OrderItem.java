package com.paypay.order.service.domain.entity;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderItem {

  private UUID orderItemId;
  private Product product;
  private Integer quantity;
  private BigDecimal price;

  private OrderItem(Builder builder) {
    orderItemId = builder.orderItemId;
    product = builder.product;
    quantity = builder.quantity;
    price = builder.price;
  }

  public UUID getOrderItemId() {
    return orderItemId;
  }

  public Product getProduct() {
    return product;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public static final class Builder {

    private UUID orderItemId;
    private Product product;
    private Integer quantity;
    private BigDecimal price;

    private Builder() {}

    public static Builder builder() {
      return new Builder();
    }

    public Builder orderItemId(UUID val) {
      orderItemId = val;
      return this;
    }

    public Builder product(Product val) {
      product = val;
      return this;
    }

    public Builder quantity(Integer val) {
      quantity = val;
      return this;
    }

    public Builder price(BigDecimal val) {
      price = val;
      return this;
    }

    public OrderItem build() {
      return new OrderItem(this);
    }
  }
}
