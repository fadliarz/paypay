package com.paypay.order.service.client.store.feign;

import com.paypay.order.service.client.store.entity.StoreDetails;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "store-service")
public interface StoreFeignClient {

  @GetMapping("/stores/{storeId}/products")
  Optional<StoreDetails> findStoreInformationWithProducts(
      @PathVariable("storeId") UUID storeId, @RequestParam("productIds") List<UUID> productIds);
}
