package com.paypay.order.service.dataaccess.customer.adapter;

import com.paypay.order.service.dataaccess.customer.mapper.CustomerDataAccessMapper;
import com.paypay.order.service.dataaccess.customer.repository.CustomerJpaRepository;
import com.paypay.order.service.domain.entity.Customer;
import com.paypay.order.service.domain.ports.output.repository.CustomerRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class CustomerRepositoryImpl implements CustomerRepository {

  private final CustomerJpaRepository customerJpaRepository;
  private final CustomerDataAccessMapper customerDataAccessMapper;

  public CustomerRepositoryImpl(
      CustomerJpaRepository customerJpaRepository,
      CustomerDataAccessMapper customerDataAccessMapper) {
    this.customerJpaRepository = customerJpaRepository;
    this.customerDataAccessMapper = customerDataAccessMapper;
  }

  @Override
  public Optional<Customer> findCustomer(UUID customerId) {
    return customerJpaRepository
        .findById(customerId)
        .map(customerDataAccessMapper::customerEntityToCustomer);
  }
}
