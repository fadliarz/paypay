package com.paypay.order.service.domain;

import com.paypay.order.service.domain.entity.Order;
import com.paypay.order.service.domain.entity.Store;
import com.paypay.order.service.domain.event.OrderCreatedEvent;

public interface OrderDomainService {

  OrderCreatedEvent validateAndInitiateOrder(Order order, Store store);
}
