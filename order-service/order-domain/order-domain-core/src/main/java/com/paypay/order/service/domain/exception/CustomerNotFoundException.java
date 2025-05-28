package com.paypay.order.service.domain.exception;

import com.paypay.domain.exception.DomainException;

public class CustomerNotFoundException extends DomainException {

  public CustomerNotFoundException() {
    super(CustomerNotFoundException.class.getName());
  }

  public CustomerNotFoundException(String message) {
    super(message);
  }

  public CustomerNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
