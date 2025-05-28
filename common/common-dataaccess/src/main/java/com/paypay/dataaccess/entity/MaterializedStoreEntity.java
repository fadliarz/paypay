package com.paypay.dataaccess.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import java.math.BigDecimal;
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
@Table(name = "store_product_materialized_view", schema = "store")
@IdClass(MaterializedStoreIdEntity.class)
public class MaterializedStoreEntity {

  @Id private UUID storeId;
  @Id private UUID productId;
  private String storeName;
  private String productImage;
  private String productName;
  private String productDescription;
  private BigDecimal productPrice;
}
