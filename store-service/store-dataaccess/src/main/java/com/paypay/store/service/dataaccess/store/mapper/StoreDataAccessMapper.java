package com.paypay.store.service.dataaccess.store.mapper;

import com.paypay.store.service.dataaccess.store.entity.MaterializedStoreAndProductEntity;
import com.store.service.domain.entity.Product;
import com.store.service.domain.entity.Store;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class StoreDataAccessMapper {

  public Store materializedStoreAndProductEntitiesToStore(
      List<MaterializedStoreAndProductEntity> materializedStoreAndProductEntities) {
    Store.Builder storeBuilder = Store.Builder.builder();
    storeBuilder.setId(materializedStoreAndProductEntities.getFirst().getStoreId());
    storeBuilder.setProducts(
        materializedStoreAndProductEntities.stream()
            .map(this::materializedStoreAndProductEntityToProduct)
            .toList());
    return storeBuilder.build();
  }

  private Product materializedStoreAndProductEntityToProduct(
      MaterializedStoreAndProductEntity materializedStoreAndProductEntity) {
    Product.Builder productBuilder = Product.Builder.builder();
    productBuilder.setId(materializedStoreAndProductEntity.getProductId());
    productBuilder.setImage(materializedStoreAndProductEntity.getProductImage());
    productBuilder.setName(materializedStoreAndProductEntity.getProductName());
    productBuilder.setDescription(materializedStoreAndProductEntity.getProductDescription());
    productBuilder.setPrice(materializedStoreAndProductEntity.getProductPrice());
    return productBuilder.build();
  }
}
