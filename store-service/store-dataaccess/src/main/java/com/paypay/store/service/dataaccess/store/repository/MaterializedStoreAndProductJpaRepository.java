package com.paypay.store.service.dataaccess.store.repository;

import com.paypay.store.service.dataaccess.store.entity.MaterializedStoreAndProductEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterializedStoreAndProductJpaRepository
    extends JpaRepository<MaterializedStoreAndProductEntity, UUID> {

  Optional<List<MaterializedStoreAndProductEntity>> findByStoreIdAndProductIdIn(
      UUID storeId, List<UUID> productIds);
}
