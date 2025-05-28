package com.paypay.order.service.domain.ports.output.repository;

import com.paypay.order.service.domain.entity.Store;
import java.util.Optional;

public interface StoreRepository {

  Optional<Store> findStoreInformation(Store store);
}
