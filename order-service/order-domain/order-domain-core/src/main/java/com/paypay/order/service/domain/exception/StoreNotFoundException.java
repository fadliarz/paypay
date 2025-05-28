package com.paypay.order.service.domain.exception;

import com.paypay.domain.exception.DomainException;

public class StoreNotFoundException extends DomainException {

  public StoreNotFoundException() {
    super(StoreNotFoundException.class.getName());
  }

  public StoreNotFoundException(String message) {
    super(message);
  }

  public StoreNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
