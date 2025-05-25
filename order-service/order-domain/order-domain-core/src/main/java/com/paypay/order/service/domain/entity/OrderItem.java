package com.paypay.order.service.domain.entity;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderItem {

  private Long id;
  private final UUID orderId;
  private final Product product;
  private final int quantity;
  private final BigDecimal subTotalPrice;

  public void initializeOrderItem(Long id) {
    this.id = id;
  }

  public void validateOrderItem() {
    this.validateSubTotalPrice();
  }

  private void validateSubTotalPrice() {
    if (!subTotalPrice.equals(product.getPrice().multiply(BigDecimal.valueOf(quantity))))
      throw new RuntimeException();
  }

  public Long getId() {
    return id;
  }

  public UUID getOrderId() {
    return orderId;
  }

  public Product getProduct() {
    return product;
  }

  public int getQuantity() {
    return quantity;
  }

  public BigDecimal getSubTotalPrice() {
    return subTotalPrice;
  }

  private OrderItem(Builder builder) {
    id = builder.id;
    orderId = builder.orderId;
    product = builder.product;
    quantity = builder.quantity;
    subTotalPrice = builder.price;
  }

  public static final class Builder {

    private Long id;
    private UUID orderId;
    private Product product;
    private int quantity;
    private BigDecimal price;

    private Builder() {}

    public static Builder builder() {
      return new Builder();
    }

    public Builder setId(Long val) {
      id = val;
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

    public Builder setQuantity(int val) {
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
