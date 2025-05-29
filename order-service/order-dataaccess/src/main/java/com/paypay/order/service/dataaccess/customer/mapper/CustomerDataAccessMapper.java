package com.paypay.order.service.dataaccess.customer.mapper;

import com.paypay.order.service.dataaccess.customer.entity.CustomerEntity;
import com.paypay.order.service.domain.entity.Customer;
import com.paypay.order.service.domain.entity.Customer.Builder;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataAccessMapper {

  public Customer customerEntityToCustomer(CustomerEntity customerEntity) {
    Builder customerBuilder = Customer.Builder.builder();
    customerBuilder.setId(customerEntity.getId());
    customerBuilder.setUsername(customerEntity.getUsername());
    customerBuilder.setFirstName(customerEntity.getFirstName());
    customerBuilder.setLastName(customerEntity.getLastName());
    return customerBuilder.build();
  }
}
