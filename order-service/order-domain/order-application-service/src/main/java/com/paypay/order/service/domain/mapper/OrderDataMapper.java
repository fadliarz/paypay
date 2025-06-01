package com.paypay.order.service.domain.mapper;

import com.paypay.domain.valueobject.PaymentOrderStatus;
import com.paypay.order.service.domain.entity.Order;
import com.paypay.order.service.domain.entity.OrderItem;
import com.paypay.order.service.domain.entity.Product;
import com.paypay.order.service.domain.entity.Store;
import com.paypay.order.service.domain.event.OrderCreatedEvent;
import com.paypay.order.service.domain.features.create.order.dto.CreateOrderCommand;
import com.paypay.order.service.domain.features.create.order.dto.OrderItemDto;
import com.paypay.order.service.domain.features.create.order.dto.ProductDto;
import com.paypay.order.service.domain.outbox.model.payment.OrderPaymentEventPayload;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class OrderDataMapper {

  public Store createOrderCommandToStore(CreateOrderCommand createOrderCommand) {
    return Store.Builder.builder()
        .setId(createOrderCommand.getStoreId())
        .setProducts(
            createOrderCommand.getItems().stream()
                .map(
                    (orderItem) ->
                        Product.Builder.builder().setId(orderItem.getProduct().getId()).build())
                .toList())
        .build();
  }

  public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand) {
    return Order.Builder.builder()
        .setCustomerId(createOrderCommand.getCustomerId())
        .setStoreId(createOrderCommand.getStoreId())
        .setDeliveryAddress(createOrderCommand.getDeliveryAddress())
        .setTotalPrice(createOrderCommand.getTotalPrice())
        .setItems(
            createOrderCommand.getItems().stream().map(this::orderItemDtoToOrderItem).toList())
        .build();
  }

  private OrderItem orderItemDtoToOrderItem(OrderItemDto orderItemDto) {
    return OrderItem.Builder.builder()
        .setProduct(productDtoToProduct(orderItemDto.getProduct()))
        .setQuantity(orderItemDto.getQuantity())
        .setSubTotalPrice(orderItemDto.getSubTotalPrice())
        .build();
  }

  private Product productDtoToProduct(ProductDto productDto) {
    return Product.Builder.builder()
        .setId(productDto.getId())
        .setImage(productDto.getImage())
        .setName(productDto.getName())
        .setDescription(productDto.getDescription())
        .setPrice(productDto.getPrice())
        .build();
  }

  public List<UUID> storeToProductIds(Store store) {
    return store.getProducts().stream().map(product -> product.getId()).toList();
  }

  public OrderPaymentEventPayload orderCreatedEventToOrderPaymentEventPayload(
      OrderCreatedEvent orderCreatedEvent) {
    return OrderPaymentEventPayload.builder()
        .orderId(orderCreatedEvent.getOrder().getId().toString())
        .customerId(orderCreatedEvent.getOrder().getCustomerId().toString())
        .price(orderCreatedEvent.getOrder().getTotalPrice())
        .createdAt(orderCreatedEvent.getCreatedAt())
        .paymentOrderStatus(PaymentOrderStatus.PENDING.name())
        .build();
  }
}
