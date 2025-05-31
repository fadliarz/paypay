package com.paypay.order.service.domain.features.create.order;

import com.paypay.order.service.domain.event.OrderCreatedEvent;
import com.paypay.order.service.domain.features.create.order.dto.CreateOrderCommand;
import org.springframework.stereotype.Component;

@Component
public class CreateOrderCommandHandler {

  private final CreateOrderHelper createOrderHelper;

  public CreateOrderCommandHandler(CreateOrderHelper createOrderHelper) {
    this.createOrderHelper = createOrderHelper;
  }

  public OrderCreatedEvent handle(CreateOrderCommand createOrderCommand) {
    OrderCreatedEvent orderCreatedEvent = createOrderHelper.persistOrder(createOrderCommand);
    return orderCreatedEvent;
  }
}
