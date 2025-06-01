package com.paypay.order.service.dataaccess.outbox.payment.repository;

import com.paypay.order.service.dataaccess.outbox.payment.entity.PaymentOutboxEntity;
import com.paypay.outbox.OutboxStatus;
import com.paypay.saga.SagaStatus;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentOutboxJpaRepository extends JpaRepository<PaymentOutboxEntity, UUID> {
  List<PaymentOutboxEntity> findByTypeAndOutboxStatusAndSagaStatusIn(
      String type, OutboxStatus outboxStatus, List<SagaStatus> sagaStatusList);
}
