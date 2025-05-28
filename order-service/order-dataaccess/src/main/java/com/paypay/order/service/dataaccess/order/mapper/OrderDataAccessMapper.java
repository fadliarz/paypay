package com.paypay.order.service.dataaccess.order.mapper;

import com.paypay.order.service.dataaccess.order.entity.OrderEntity;
import com.paypay.order.service.dataaccess.order.entity.OrderItemEntity;
import com.paypay.order.service.domain.entity.Order;
import com.paypay.order.service.domain.entity.OrderItem;
import com.paypay.order.service.domain.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class OrderDataAccessMapper {

  public OrderEntity orderToOrderEntity(Order order) {
    return OrderEntity.builder()
        .id(order.getId())
        .customerId(order.getCustomerId())
        .storeId(order.getStoreId())
        .totalPrice(order.getTotalPrice())
        .items(order.getItems().stream().map(this::orderItemToOrderItemEntity).toList())
        .build();
  }

  public Order orderEntityToOrder(OrderEntity orderEntity) {
    return Order.Builder.builder()
        .setId(orderEntity.getId())
        .setCustomerId(orderEntity.getCustomerId())
        .setStoreId(orderEntity.getStoreId())
        .setTotalPrice(orderEntity.getTotalPrice())
        .setItems(orderEntity.getItems().stream().map(this::orderItemEntityToOrderItem).toList())
        .build();
  }

  private OrderItemEntity orderItemToOrderItemEntity(OrderItem orderItem) {
    return OrderItemEntity.builder()
        .productId(orderItem.getProduct().getId())
        .image(orderItem.getProduct().getImage())
        .name(orderItem.getProduct().getName())
        .description(orderItem.getProduct().getDescription())
        .price(orderItem.getProduct().getPrice())
        .quantity(orderItem.getQuantity())
        .subTotalPrice(orderItem.getSubTotalPrice())
        .build();
  }

  private OrderItem orderItemEntityToOrderItem(OrderItemEntity orderItemEntity) {
    return OrderItem.Builder.builder()
        .setProduct(orderItemEntityToProduct(orderItemEntity))
        .setQuantity(orderItemEntity.getQuantity())
        .setSubTotalPrice(orderItemEntity.getSubTotalPrice())
        .build();
  }

  private Product orderItemEntityToProduct(OrderItemEntity orderItemEntity) {
    return Product.Builder.builder()
        .setId(orderItemEntity.getProductId())
        .setImage(orderItemEntity.getImage())
        .setName(orderItemEntity.getName())
        .setDescription(orderItemEntity.getDescription())
        .setPrice(orderItemEntity.getPrice())
        .build();
  }
}
