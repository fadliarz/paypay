package com.paypay.order.service.domain.entity;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderItem {

  private final UUID orderItemId;
  private final UUID orderId;
  private final Product product;
  private final Integer quantity;
  private final BigDecimal price;

  private OrderItem(Builder builder) {
    orderItemId = builder.orderItemId;
    orderId = builder.orderId;
    product = builder.product;
    quantity = builder.quantity;
    price = builder.price;
  }

  public UUID getOrderItemId() {
    return orderItemId;
  }

  public UUID getOrderId() {
    return orderId;
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
    private UUID orderId;
    private Product product;
    private Integer quantity;
    private BigDecimal price;

    private Builder() {}

    public static Builder builder() {
      return new Builder();
    }

    public Builder setOrderItemId(UUID val) {
      orderItemId = val;
      return this;
    }

    public Builder setOrderId(UUID val) {
      orderId = val;
      return this;
    }

    public Builder setProduct(Product val) {
      product = val;
      return this;
    }

    public Builder setQuantity(Integer val) {
      quantity = val;
      return this;
    }

    public Builder setPrice(BigDecimal val) {
      price = val;
      return this;
    }

    public OrderItem build() {
      return new OrderItem(this);
    }
  }
}
