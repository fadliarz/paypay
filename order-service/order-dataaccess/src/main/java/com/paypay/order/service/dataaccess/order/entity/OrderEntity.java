package com.paypay.order.service.dataaccess.order.entity;

import com.paypay.domain.valueobject.OrderStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.List;
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
@Table(schema = "order", name = "orders")
@Entity
public class OrderEntity {

  @Id private UUID id;
  private UUID customerId;
  private UUID storeId;
  private String deliveryAddress;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  private List<OrderItemEntity> items;

  private BigDecimal totalPrice;

  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;
}
