package com.paypay.order.service.domain.outbox.model.payment;

import com.paypay.domain.valueobject.OrderStatus;
import com.paypay.outbox.OutboxStatus;
import com.paypay.saga.SagaStatus;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class OrderPaymentOutboxMessage {
  private UUID id;
  private UUID sagaId;
  private ZonedDateTime createdAt;
  private ZonedDateTime processedAt;
  private String type;
  private String payload;
  private OrderStatus orderStatus;
  private SagaStatus sagaStatus;
  private OutboxStatus outboxStatus;
  private int version;

  public void setProcessedAt(ZonedDateTime processedAt) {
    this.processedAt = processedAt;
  }

  public void setOrderStatus(OrderStatus orderStatus) {
    this.orderStatus = orderStatus;
  }

  public void setSagaStatus(SagaStatus sagaStatus) {
    this.sagaStatus = sagaStatus;
  }

  public void setOutboxStatus(OutboxStatus outboxStatus) {
    this.outboxStatus = outboxStatus;
  }
}
