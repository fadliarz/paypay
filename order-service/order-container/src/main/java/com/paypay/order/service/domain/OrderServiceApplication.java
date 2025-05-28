package com.paypay.order.service.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(
    basePackages = {"com.paypay.order.service.dataaccess", "com.paypay.dataaccess"})
@EntityScan(basePackages = {"com.paypay.order.service.dataaccess", "com.paypay.dataaccess"})
@SpringBootApplication(scanBasePackages = "com.paypay")
public class OrderServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(OrderServiceApplication.class, args);
  }
}
