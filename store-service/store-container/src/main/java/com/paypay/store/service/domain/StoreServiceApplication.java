package com.paypay.store.service.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.paypay")
@EntityScan(basePackages = "com.paypay")
@EnableJpaRepositories(
    basePackages = {"com.paypay.store.service.dataaccess", "com.paypay.dataaccess"})
public class StoreServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(StoreServiceApplication.class, args);
  }
}
