package com.paypay.order.service.domain.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class Order {
  private UUID id;
  private final UUID customerId;
  private final UUID storeId;
  private final String deliveryAddress;
  private final List<OrderItem> orderItems;
  private final BigDecimal price;

  public void initializeOrder() {
    this.id = UUID.randomUUID();
    initializeOrderItems();
  }

  public void validateOrder() {
    validateOrderItems();
  }

  private void initializeOrderItems() {
    long id = 1;
    for (OrderItem orderItem : orderItems) {
      orderItem.initializeOrderItem(id++);
    }
  }

  private void validateOrderItems() {
    BigDecimal totalPrice =
        orderItems.stream()
            .map(
                (orderItem) -> {
                  orderItem.validateOrderItem();
                  return orderItem.getSubTotalPrice();
                })
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    if (!price.equals(totalPrice)) throw new RuntimeException();
  }

  public UUID getId() {
    return id;
  }

  public UUID getCustomerId() {
    return customerId;
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

  private Order(Builder builder) {
    orderItems = builder.orderItems;
    id = builder.id;
    customerId = builder.customerId;
    storeId = builder.storeId;
    deliveryAddress = builder.deliveryAddress;
    price = builder.price;
  }

  public static final class Builder {

    private List<OrderItem> orderItems;
    private UUID id;
    private UUID customerId;
    private UUID storeId;
    private String deliveryAddress;
    private BigDecimal price;

    private Builder() {}

    public static Builder builder() {
      return new Builder();
    }

    public Builder setOrderItems(List<OrderItem> val) {
      orderItems = val;
      return this;
    }

    public Builder setId(UUID val) {
      id = val;
      return this;
    }

    public Builder setCustomerId(UUID val) {
      customerId = val;
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

    public Builder setPrice(BigDecimal val) {
      price = val;
      return this;
    }

    public Order build() {
      return new Order(this);
    }
  }
}
