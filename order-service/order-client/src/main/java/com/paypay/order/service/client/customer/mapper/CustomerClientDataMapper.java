package com.paypay.order.service.client.customer.mapper;

import com.paypay.order.service.client.customer.dto.CustomerDetailsDto;
import com.paypay.order.service.domain.entity.Customer;
import com.paypay.order.service.domain.entity.Customer.Builder;
import org.springframework.stereotype.Component;

@Component
public class CustomerClientDataMapper {

  public Customer customerDetailsDtoToCustomer(CustomerDetailsDto customerDetailsDto) {
    Builder customerBuilder = Customer.Builder.builder();
    customerBuilder.setId(customerDetailsDto.getId());
    customerBuilder.setUsername(customerDetailsDto.getUsername());
    customerBuilder.setFirstName(customerDetailsDto.getFirstName());
    customerBuilder.setLastName(customerDetailsDto.getLastName());
    return customerBuilder.build();
  }
}
