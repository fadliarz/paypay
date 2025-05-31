package com.paypay.order.service.domain.entity;

import com.paypay.domain.valueobject.OrderStatus;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Order {
  private UUID id;
  private final UUID customerId;
  private final UUID storeId;
  private final String deliveryAddress;
  private final List<OrderItem> items;
  private final BigDecimal totalPrice;
  private OrderStatus orderStatus;

  private Order(Builder builder) {
    id = builder.id;
    customerId = builder.customerId;
    storeId = builder.storeId;
    deliveryAddress = builder.deliveryAddress;
    items = builder.items;
    totalPrice = builder.totalPrice;
    orderStatus = builder.orderStatus;
  }

  public void initializeOrder() {
    this.id = UUID.randomUUID();
    this.orderStatus = OrderStatus.PENDING;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Order order = (Order) o;
    return Objects.equals(id, order.id)
        && Objects.equals(customerId, order.customerId)
        && Objects.equals(storeId, order.storeId)
        && Objects.equals(deliveryAddress, order.deliveryAddress)
        && Objects.equals(items, order.items)
        && Objects.equals(totalPrice, order.totalPrice)
        && orderStatus == order.orderStatus;
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

  public OrderStatus getOrderStatus() {
    return orderStatus;
  }

  public static final class Builder {

    private UUID id;
    private UUID customerId;
    private UUID storeId;
    private String deliveryAddress;
    private List<OrderItem> items;
    private BigDecimal totalPrice;
    private OrderStatus orderStatus;

    private Builder() {}

    public static Builder builder() {
      return new Builder();
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

    public Builder setItems(List<OrderItem> val) {
      items = val;
      return this;
    }

    public Builder setTotalPrice(BigDecimal val) {
      totalPrice = val;
      return this;
    }

    public Builder setOrderStatus(OrderStatus val) {
      orderStatus = val;
      return this;
    }

    public Order build() {
      return new Order(this);
    }
  }
}
