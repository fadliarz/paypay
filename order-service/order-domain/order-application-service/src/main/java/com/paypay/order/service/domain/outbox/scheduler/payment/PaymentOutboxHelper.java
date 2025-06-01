package com.paypay.order.service.domain.outbox.scheduler.payment;

import static com.paypay.saga.order.SagaConstants.ORDER_SAGA_NAME;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypay.domain.valueobject.OrderStatus;
import com.paypay.order.service.domain.outbox.model.payment.OrderPaymentEventPayload;
import com.paypay.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage;
import com.paypay.order.service.domain.ports.output.repository.PaymentOutboxRepository;
import com.paypay.outbox.OutboxStatus;
import com.paypay.saga.SagaStatus;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentOutboxHelper {

  private final ObjectMapper objectMapper;
  private final PaymentOutboxRepository paymentOutboxRepository;

  public PaymentOutboxHelper(
      ObjectMapper objectMapper, PaymentOutboxRepository paymentOutboxRepository) {
    this.objectMapper = objectMapper;
    this.paymentOutboxRepository = paymentOutboxRepository;
  }

  public void savePaymentOutboxMessage(
      OrderPaymentEventPayload orderPaymentEventPayload,
      OrderStatus orderStatus,
      SagaStatus sagaStatus,
      OutboxStatus outboxStatus,
      UUID sagaId) {
    save(
        OrderPaymentOutboxMessage.builder()
            .id(UUID.randomUUID())
            .sagaId(sagaId)
            .createdAt(orderPaymentEventPayload.getCreatedAt())
            .type(ORDER_SAGA_NAME)
            .payload(serializePayload(orderPaymentEventPayload))
            .orderStatus(orderStatus)
            .sagaStatus(sagaStatus)
            .outboxStatus(outboxStatus)
            .build());
  }

  public void save(OrderPaymentOutboxMessage orderPaymentOutboxMessage) {
    paymentOutboxRepository.save(orderPaymentOutboxMessage);
  }

  private String serializePayload(OrderPaymentEventPayload orderPaymentEventPayload) {
    return serializeObject(orderPaymentEventPayload);
  }

  private String serializeObject(Object object) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
