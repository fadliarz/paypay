package com.paypay.order.service.domain;

import com.paypay.order.service.domain.entity.Order;
import com.paypay.order.service.domain.entity.Product;
import com.paypay.order.service.domain.entity.Store;
import com.paypay.order.service.domain.event.OrderCreatedEvent;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.UUID;

public class OrderDomainServiceImpl implements OrderDomainService {

  @Override
  public OrderCreatedEvent validateAndInitiateOrder(Order order, Store store) {
    validateOrderProducts(order, store);
    order.validateOrder();
    order.initializeOrder();
    return new OrderCreatedEvent(order, ZonedDateTime.now());
  }

  private void validateOrderProducts(Order order, Store store) {
    HashMap<UUID, Product> storeProductHashMap = new HashMap<>();
    store.getProducts().forEach((product) -> storeProductHashMap.put(product.getId(), product));
    order
        .getItems()
        .forEach(
            (orderItem -> {
              Product orderItemProduct = orderItem.getProduct();
              Product storeProduct = storeProductHashMap.get(orderItemProduct.getId());
              if (storeProduct == null) throw new IllegalArgumentException();
              if (!orderItemProduct.matches(storeProduct)) throw new IllegalArgumentException();
            }));
  }
}
