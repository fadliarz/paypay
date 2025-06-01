package com.paypay.order.service.domain;

import com.paypay.domain.valueobject.OrderStatus;
import com.paypay.saga.SagaStatus;
import org.springframework.stereotype.Component;

@Component
public class OrderSagaHelper {

  public SagaStatus orderStatusToSagaStatus(OrderStatus orderStatus) {
    switch (orderStatus) {
      case PAID:
        return SagaStatus.PROCESSING;
      case APPROVED:
        return SagaStatus.SUCCEEDED;
      case CANCELLING:
        return SagaStatus.COMPENSATING;
      case CANCELLED:
        return SagaStatus.COMPENSATED;
      default:
        return SagaStatus.STARTED;
    }
  }
}
