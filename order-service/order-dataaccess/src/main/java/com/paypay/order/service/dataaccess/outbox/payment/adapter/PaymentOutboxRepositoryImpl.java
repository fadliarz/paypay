package com.paypay.order.service.dataaccess.outbox.payment.adapter;

import com.paypay.order.service.dataaccess.outbox.payment.mapper.PaymentOutboxDataAccessMapper;
import com.paypay.order.service.dataaccess.outbox.payment.repository.PaymentOutboxJpaRepository;
import com.paypay.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage;
import com.paypay.order.service.domain.ports.output.repository.PaymentOutboxRepository;
import com.paypay.outbox.OutboxStatus;
import com.paypay.saga.SagaStatus;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PaymentOutboxRepositoryImpl implements PaymentOutboxRepository {

  private final PaymentOutboxDataAccessMapper paymentOutboxDataAccessMapper;
  private final PaymentOutboxJpaRepository paymentOutboxJpaRepository;

  public PaymentOutboxRepositoryImpl(
      PaymentOutboxDataAccessMapper paymentOutboxDataAccessMapper,
      PaymentOutboxJpaRepository paymentOutboxJpaRepository) {
    this.paymentOutboxDataAccessMapper = paymentOutboxDataAccessMapper;
    this.paymentOutboxJpaRepository = paymentOutboxJpaRepository;
  }

  @Override
  public OrderPaymentOutboxMessage save(OrderPaymentOutboxMessage orderPaymentOutboxMessage) {
    return paymentOutboxDataAccessMapper.paymentOutboxEntityToOrderPaymentOutboxMessage(
        paymentOutboxJpaRepository.save(
            paymentOutboxDataAccessMapper.orderPaymentOutboxMessageToPaymentOutboxEntity(
                orderPaymentOutboxMessage)));
  }

  @Override
  public List<OrderPaymentOutboxMessage> findByTypeAndOutboxStatusAndSagaStatusIn(
      String type, OutboxStatus outboxStatus, SagaStatus... sagaStatusArray) {
    return paymentOutboxJpaRepository
        .findByTypeAndOutboxStatusAndSagaStatusIn(
            type, outboxStatus, Arrays.asList(sagaStatusArray))
        .stream()
        .map(paymentOutboxDataAccessMapper::paymentOutboxEntityToOrderPaymentOutboxMessage)
        .toList();
  }
}
