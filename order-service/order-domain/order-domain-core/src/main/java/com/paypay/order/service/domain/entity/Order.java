package com.paypay.order.service.domain.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class Order {
  private UUID id;
  private final UUID customerId;
  private final UUID storeId;
  private final String deliveryAddress;
  private final List<OrderItem> items;
  private final BigDecimal totalPrice;

  public void initializeOrder() {
    this.id = UUID.randomUUID();
    initializeOrderItems();
  }

  public void validateOrder() {
    validateOrderItems();
  }

  private void initializeOrderItems() {
    long id = 1;
    for (OrderItem orderItem : items) {
      orderItem.initializeOrderItem(id++);
    }
  }

  private void validateOrderItems() {
    BigDecimal totalPrice =
        items.stream()
            .map(
                (orderItem) -> {
                  orderItem.validateOrderItem();
                  return orderItem.getSubTotalPrice();
                })
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    if (!this.totalPrice.equals(totalPrice)) throw new RuntimeException();
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

  public List<OrderItem> getItems() {
    return items;
  }

  public BigDecimal getTotalPrice() {
    return totalPrice;
  }

  private Order(Builder builder) {
    items = builder.items;
    id = builder.id;
    customerId = builder.customerId;
    storeId = builder.storeId;
    deliveryAddress = builder.deliveryAddress;
    totalPrice = builder.totalPrice;
  }

  public static final class Builder {

    private List<OrderItem> items;
    private UUID id;
    private UUID customerId;
    private UUID storeId;
    private String deliveryAddress;
    private BigDecimal totalPrice;

    private Builder() {}

    public static Builder builder() {
      return new Builder();
    }

    public Builder setItems(List<OrderItem> val) {
      items = val;
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

    public Builder setTotalPrice(BigDecimal val) {
      totalPrice = val;
      return this;
    }

    public Order build() {
      return new Order(this);
    }
  }
}
