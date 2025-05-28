package com.paypay.order.service.dataaccess.order.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderItemIdEntity implements Serializable {
  private Long id;
  private OrderEntity order;
}
