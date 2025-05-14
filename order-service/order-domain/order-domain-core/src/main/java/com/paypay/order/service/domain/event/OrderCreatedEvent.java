package com.paypay.order.service.domain.event;

import com.paypay.order.service.domain.entity.Order;
import java.time.ZonedDateTime;

public class OrderCreatedEvent extends OrderEvent {

  public OrderCreatedEvent(Order oder, ZonedDateTime createdAt) {
    super(oder, createdAt);
  }
}
