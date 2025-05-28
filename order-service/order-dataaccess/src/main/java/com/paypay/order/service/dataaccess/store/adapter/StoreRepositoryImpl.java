package com.paypay.order.service.dataaccess.store.adapter;

import com.paypay.dataaccess.entity.MaterializedStoreEntity;
import com.paypay.dataaccess.repository.StoreJPARepository;
import com.paypay.order.service.dataaccess.store.mapper.StoreDataAccessMapper;
import com.paypay.order.service.domain.entity.Product;
import com.paypay.order.service.domain.entity.Store;
import com.paypay.order.service.domain.ports.output.repository.StoreRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class StoreRepositoryImpl implements StoreRepository {

  private final StoreJPARepository storeJPARepository;
  private final StoreDataAccessMapper storeDataAccessMapper;

  public StoreRepositoryImpl(
      StoreJPARepository storeJPARepository, StoreDataAccessMapper storeDataAccessMapper) {
    this.storeJPARepository = storeJPARepository;
    this.storeDataAccessMapper = storeDataAccessMapper;
  }

  @Override
  public Optional<Store> findStoreInformation(Store store) {
    Optional<List<MaterializedStoreEntity>> materializedStoreEntities =
        storeJPARepository.findByStoreIdAndProductIdIn(
            store.getId(), store.getProducts().stream().map(Product::getId).toList());
    return materializedStoreEntities.map(storeDataAccessMapper::materializedStoreEntitiesToStore);
  }
}
