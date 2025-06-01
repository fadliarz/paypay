package com.paypay.saga;

public enum SagaStatus {
  STARTED,
  FAILED,
  SUCCEEDED,
  PROCESSING,
  COMPENSATING,
  COMPENSATED
}
