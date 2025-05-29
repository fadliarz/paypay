package com.store.service.domain.ports.repository;

import com.store.service.domain.entity.Store;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StoreRepository {

  Optional<Store> findStoreInformationWithProducts(UUID storeId, List<UUID> productIds);
}
