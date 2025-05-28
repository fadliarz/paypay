package com.paypay.order.service.client.customer.client;

import com.paypay.order.service.client.customer.dto.CustomerDetailsDto;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "customer-service")
public interface CustomerFeignClient {
  @GetMapping("/customers/${customerId}")
  CustomerDetailsDto findCustomer(@PathVariable("customerId") UUID customerId);
}
