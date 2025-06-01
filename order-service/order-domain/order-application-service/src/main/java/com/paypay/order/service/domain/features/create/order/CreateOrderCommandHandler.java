package com.paypay.order.service.domain.features.create.order;

import com.paypay.order.service.domain.OrderSagaHelper;
import com.paypay.order.service.domain.event.OrderCreatedEvent;
import com.paypay.order.service.domain.features.create.order.dto.CreateOrderCommand;
import com.paypay.order.service.domain.mapper.OrderDataMapper;
import com.paypay.order.service.domain.outbox.scheduler.payment.PaymentOutboxHelper;
import com.paypay.outbox.OutboxStatus;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class CreateOrderCommandHandler {

  private final CreateOrderHelper createOrderHelper;
  private final PaymentOutboxHelper paymentOutboxHelper;
  private final OrderSagaHelper orderSagaHelper;
  private final OrderDataMapper orderDataMapper;

  public CreateOrderCommandHandler(
      CreateOrderHelper createOrderHelper,
      PaymentOutboxHelper paymentOutboxHelper,
      OrderSagaHelper orderSagaHelper,
      OrderDataMapper orderDataMapper,
      OrderDataMapper orderDataMapper1) {
    this.createOrderHelper = createOrderHelper;
    this.paymentOutboxHelper = paymentOutboxHelper;
    this.orderSagaHelper = orderSagaHelper;
    this.orderDataMapper = orderDataMapper1;
  }

  public OrderCreatedEvent handle(CreateOrderCommand createOrderCommand) {
    OrderCreatedEvent orderCreatedEvent = createOrderHelper.persistOrder(createOrderCommand);

    paymentOutboxHelper.savePaymentOutboxMessage(
        orderDataMapper.orderCreatedEventToOrderPaymentEventPayload(orderCreatedEvent),
        orderCreatedEvent.getOrder().getOrderStatus(),
        orderSagaHelper.orderStatusToSagaStatus(orderCreatedEvent.getOrder().getOrderStatus()),
        OutboxStatus.STARTED,
        UUID.randomUUID());

    return orderCreatedEvent;
  }
}
