package com.paypay.order.service.domain.ports.output.client;

import com.paypay.order.service.domain.entity.Store;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StoreClient {

  Optional<Store> findStoreInformationWithProducts(UUID storeId, List<UUID> productIds);
}
