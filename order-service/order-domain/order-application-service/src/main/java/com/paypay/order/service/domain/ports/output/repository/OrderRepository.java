package com.paypay.order.service.domain.ports.output.repository;

import com.paypay.order.service.domain.entity.Order;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

  Order save(Order order);

  Optional<Order> findOrder(UUID orderId);
}
