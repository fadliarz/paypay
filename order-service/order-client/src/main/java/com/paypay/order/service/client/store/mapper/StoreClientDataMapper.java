package com.paypay.order.service.client.store.mapper;

import com.paypay.order.service.client.store.entity.ProductDetails;
import com.paypay.order.service.client.store.entity.StoreDetails;
import com.paypay.order.service.domain.entity.Product;
import com.paypay.order.service.domain.entity.Store;
import com.paypay.order.service.domain.entity.Store.Builder;
import org.springframework.stereotype.Component;

@Component
public class StoreClientDataMapper {

  public Store storeDetailsToStore(StoreDetails storeDetails) {
    Store.Builder storeBuilder = Store.Builder.builder();
    storeBuilder.setId(storeDetails.getId());
    storeBuilder.setProducts(
        storeDetails.getProducts().stream().map(this::productDetailsToProduct).toList());
    return storeBuilder.build();
  }

  public Product productDetailsToProduct(ProductDetails productDetails) {
    Product.Builder productBuilder = Product.Builder.builder();
    productBuilder.setId(productDetails.getId());
    productBuilder.setImage(productDetails.getImage());
    productBuilder.setName(productDetails.getName());
    productBuilder.setDescription(productDetails.getDescription());
    productBuilder.setPrice(productDetails.getPrice());
    return productBuilder.build();
  }
}
