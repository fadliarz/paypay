package com.paypay.store.service.dataaccess.store.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import java.math.BigDecimal;
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
@Entity
@Table(name = "store_product_m_view", schema = "store")
@IdClass(MaterializedStoreAndProductIdEntity.class)
public class MaterializedStoreAndProductEntity {

  @Id private UUID storeId;
  @Id private UUID productId;
  private String productImage;
  private String productName;
  private String productDescription;
  private BigDecimal productPrice;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MaterializedStoreAndProductEntity that = (MaterializedStoreAndProductEntity) o;
    return Objects.equals(storeId, that.storeId) && Objects.equals(productId, that.productId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        storeId, productId, productImage, productName, productDescription, productPrice);
  }
}
