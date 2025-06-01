package com.paypay.order.service.dataaccess.outbox.payment.entity;

import com.paypay.domain.valueobject.OrderStatus;
import com.paypay.outbox.OutboxStatus;
import com.paypay.saga.SagaStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import java.util.UUID;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(schema = "order", name = "payment_outbox")
public class PaymentOutboxEntity {

  @Id private UUID id;
  private UUID sagaId;
  private ZonedDateTime createdAt;
  private ZonedDateTime processedAt;
  private String type;
  private String payload;

  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

  @Enumerated(EnumType.STRING)
  private SagaStatus sagaStatus;

  @Enumerated(EnumType.STRING)
  private OutboxStatus outboxStatus;

  @Version private int version;
}
