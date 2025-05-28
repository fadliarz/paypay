package com.paypay.dataaccess.repository;

import com.paypay.dataaccess.entity.MaterializedStoreEntity;
import com.paypay.dataaccess.entity.MaterializedStoreIdEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreJPARepository
    extends JpaRepository<MaterializedStoreEntity, MaterializedStoreIdEntity> {

  Optional<List<MaterializedStoreEntity>> findByStoreIdAndProductIdIn(
      UUID storeId, List<UUID> productIds);
}
