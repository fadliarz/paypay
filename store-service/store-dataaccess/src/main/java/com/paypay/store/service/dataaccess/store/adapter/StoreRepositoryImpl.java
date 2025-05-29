package com.paypay.store.service.dataaccess.store.adapter;

import com.paypay.store.service.dataaccess.store.mapper.StoreDataAccessMapper;
import com.paypay.store.service.dataaccess.store.repository.MaterializedStoreAndProductJpaRepository;
import com.store.service.domain.entity.Store;
import com.store.service.domain.ports.repository.StoreRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class StoreRepositoryImpl implements StoreRepository {

  private final MaterializedStoreAndProductJpaRepository materializedStoreAndProductJpaRepository;
  private final StoreDataAccessMapper storeDataAccessMapper;

  public StoreRepositoryImpl(
      MaterializedStoreAndProductJpaRepository materializedStoreAndProductJpaRepository,
      StoreDataAccessMapper storeDataAccessMapper) {
    this.materializedStoreAndProductJpaRepository = materializedStoreAndProductJpaRepository;
    this.storeDataAccessMapper = storeDataAccessMapper;
  }

  @Override
  public Optional<Store> findStoreInformationWithProducts(UUID storeId, List<UUID> productIds) {
    return materializedStoreAndProductJpaRepository
        .findByStoreIdAndProductIdIn(storeId, productIds)
        .map(storeDataAccessMapper::materializedStoreAndProductEntitiesToStore);
  }
}
