package com.paypay.order.service.domain.ports.output.repository;

import com.paypay.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage;
import com.paypay.outbox.OutboxStatus;
import com.paypay.saga.SagaStatus;
import java.util.List;

public interface PaymentOutboxRepository {

  OrderPaymentOutboxMessage save(OrderPaymentOutboxMessage orderPaymentOutboxMessage);

  List<OrderPaymentOutboxMessage> findByTypeAndOutboxStatusAndSagaStatusIn(
      String type, OutboxStatus outboxStatus, SagaStatus... sagaStatusArray);
}
