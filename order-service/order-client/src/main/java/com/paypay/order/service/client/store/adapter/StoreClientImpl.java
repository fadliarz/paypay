package com.paypay.order.service.client.store.adapter;

import com.paypay.order.service.client.store.feign.StoreFeignClient;
import com.paypay.order.service.client.store.mapper.StoreClientDataMapper;
import com.paypay.order.service.domain.entity.Store;
import com.paypay.order.service.domain.ports.output.client.StoreClient;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class StoreClientImpl implements StoreClient {

  private final StoreFeignClient storeFeignClient;
  private final StoreClientDataMapper storeClientDataMapper;

  public StoreClientImpl(
      StoreFeignClient storeFeignClient, StoreClientDataMapper storeClientDataMapper) {
    this.storeFeignClient = storeFeignClient;
    this.storeClientDataMapper = storeClientDataMapper;
  }

  @Override
  public Optional<Store> findStoreInformationWithProducts(UUID storeId, List<UUID> productIds) {
    return storeFeignClient
        .findStoreInformationWithProducts(storeId, productIds)
        .map(storeClientDataMapper::storeDetailsToStore);
  }
}
