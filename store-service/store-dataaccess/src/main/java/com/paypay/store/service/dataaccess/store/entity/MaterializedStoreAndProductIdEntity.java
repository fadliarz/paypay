package com.paypay.store.service.dataaccess.store.entity;

import java.io.Serializable;
import java.util.Objects;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MaterializedStoreAndProductIdEntity that = (MaterializedStoreAndProductIdEntity) o;
    return Objects.equals(storeId, that.storeId) && Objects.equals(productId, that.productId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(storeId, productId);
  }
}
