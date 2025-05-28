package com.paypay.order.service.client.customer.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerDetailsDto {

  private UUID id;
  private String username;
  private String firstName;
  private String lastName;
}
