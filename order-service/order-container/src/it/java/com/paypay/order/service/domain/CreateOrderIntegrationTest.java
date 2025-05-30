package com.paypay.order.service.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.paypay.order.service.domain.command.CommandFactory;
import com.paypay.order.service.domain.entity.Store;
import com.paypay.order.service.domain.exception.CustomerNotFoundException;
import com.paypay.order.service.domain.exception.StoreNotFoundException;
import com.paypay.order.service.domain.features.create.order.CreateOrderCommandHandler;
import com.paypay.order.service.domain.ports.output.client.StoreClient;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(classes = {OrderServiceApplication.class})
@Testcontainers
public class CreateOrderIntegrationTest extends AbstractOrderIntegrationTest {

  @Autowired private CreateOrderCommandHandler createOrderCommandHandler;
  @Autowired private StoreClient storeClient;

  @Test
  void placeOrder_whenCommandIsValid_persistOrderAndItems() {
    mockFindStoreWithProducts200Ok();

    Optional<Store> optionalStore =
        storeClient.findStoreInformationWithProducts(
            mockedStoreId,
            mockedProducts.stream().map(mockedProduct -> mockedProduct.getId()).toList());

    assertFalse(optionalStore.isEmpty());
    Store store = optionalStore.get();
    assertNotNull(store);
    assertEquals(mockedStoreDetails.getId(), store.getId());
    assertEquals(mockedStoreDetails.getProducts().size(), store.getProducts().size());
  }

  @Test
  void placeOrder_whenStoreNotFound_throwStoreNotFoundException() {
    mockFindStoreWithProduct404NotFound();

    assertNotNull(createOrderCommand);
    assertThrows(
        StoreNotFoundException.class, () -> createOrderCommandHandler.handle(createOrderCommand));
  }

  @Test
  void placeOrder_whenCustomerNotFound__throwCustomerNotFoundException() {
    assertThrows(
        CustomerNotFoundException.class,
        () -> createOrderCommandHandler.handle(CommandFactory.newCreateOrderCommand()));
  }
}
