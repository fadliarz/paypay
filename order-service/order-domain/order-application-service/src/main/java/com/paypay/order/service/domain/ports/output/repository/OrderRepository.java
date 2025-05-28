package com.paypay.order.service.domain.ports.output.repository;

import com.paypay.order.service.domain.entity.Order;

public interface OrderRepository {

  Order save(Order order);
}
