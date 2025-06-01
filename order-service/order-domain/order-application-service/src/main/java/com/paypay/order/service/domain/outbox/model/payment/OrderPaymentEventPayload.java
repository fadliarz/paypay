package com.paypay.order.service.domain.outbox.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
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
public class OrderPaymentEventPayload {

  @JsonProperty private String orderId;
  @JsonProperty private String customerId;
  @JsonProperty private BigDecimal price;
  @JsonProperty private ZonedDateTime createdAt;
  @JsonProperty private String paymentOrderStatus;
}
