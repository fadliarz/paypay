package com.paypay.order.service.dataaccess.store.mapper;

import com.paypay.dataaccess.entity.MaterializedStoreEntity;
import com.paypay.order.service.domain.entity.Product;
import com.paypay.order.service.domain.entity.Store;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class StoreDataAccessMapper {

  public Store materializedStoreEntitiesToStore(
      List<MaterializedStoreEntity> materializedStoreEntities) {
    MaterializedStoreEntity materializedStoreEntity =
        materializedStoreEntities.stream().findFirst().orElseThrow(() -> new RuntimeException(""));
    List<Product> products =
        materializedStoreEntities.stream()
            .map(
                (materializedStoreEntity1 ->
                    Product.Builder.builder()
                        .setId(materializedStoreEntity.getProductId())
                        .build()))
            .toList();
    return Store.Builder.builder()
        .setId(materializedStoreEntity.getStoreId())
        .setProducts(products)
        .build();
  }
}
