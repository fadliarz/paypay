package com.paypay.order.service.client.customer.adapter;

import com.paypay.order.service.client.customer.client.CustomerFeignClient;
import com.paypay.order.service.client.customer.dto.CustomerDetailsDto;
import com.paypay.order.service.client.customer.mapper.CustomerClientDataMapper;
import com.paypay.order.service.domain.entity.Customer;
import com.paypay.order.service.domain.ports.output.client.CustomerClient;
import feign.FeignException;
import feign.FeignException.NotFound;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomerClientImpl implements CustomerClient {

  private final CustomerFeignClient customerFeignClient;
  private final CustomerClientDataMapper customerClientDataMapper;

  public CustomerClientImpl(
      CustomerFeignClient customerFeignClient, CustomerClientDataMapper customerClientDataMapper) {
    this.customerFeignClient = customerFeignClient;
    this.customerClientDataMapper = customerClientDataMapper;
  }

  @Override
  public Optional<Customer> findCustomer(UUID customerId) {
    try {
      CustomerDetailsDto customerDetailsDto = customerFeignClient.findCustomer(customerId);
      return Optional.of(customerClientDataMapper.customerDetailsDtoToCustomer(customerDetailsDto));
    } catch (NotFound notFound) {
      return Optional.empty();
    } catch (FeignException feignException) {
      // ToDo: Add logger
      throw feignException;
    }
  }
}
