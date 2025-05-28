package com.paypay.order.service.domain.ports.output.client;

import com.paypay.order.service.domain.entity.Customer;
import java.util.Optional;
import java.util.UUID;

public interface CustomerClient {

  Optional<Customer> findCustomer(UUID customerId);
}
