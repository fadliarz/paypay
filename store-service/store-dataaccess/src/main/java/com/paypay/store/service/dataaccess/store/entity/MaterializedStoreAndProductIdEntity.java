package com.paypay.store.service.dataaccess.store.entity;

import java.io.Serializable;
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
public class MaterializedStoreAndProductIdEntity implements Serializable {

  private UUID storeId;
  private UUID productId;
}
