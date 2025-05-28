package com.paypay.order.service.dataaccess.customer.mapper;

import com.paypay.order.service.dataaccess.customer.entity.CustomerEntity;
import com.paypay.order.service.domain.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataAccessMapper {

  public CustomerEntity customerToCustomerEntity(Customer customer) {
    return CustomerEntity.builder()
        .id(customer.getId())
        .username(customer.getUsername())
        .firstName(customer.getFirstName())
        .lastName(customer.getLastName())
        .build();
  }

  public Customer customerEntityToCustomer(CustomerEntity customerEntity) {
    return Customer.Builder.builder()
        .setId(customerEntity.getId())
        .setUsername(customerEntity.getUsername())
        .setFirstName(customerEntity.getFirstName())
        .setLastName(customerEntity.getLastName())
        .build();
  }
}
