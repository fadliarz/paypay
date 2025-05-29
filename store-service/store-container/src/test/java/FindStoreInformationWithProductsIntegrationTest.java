import static org.junit.jupiter.api.Assertions.*;

import com.paypay.store.service.domain.StoreServiceApplication;
import com.store.service.domain.entity.Product;
import com.store.service.domain.entity.Store;
import com.store.service.domain.ports.repository.StoreRepository;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

@Slf4j
@SpringBootTest(classes = {StoreServiceApplication.class})
@Testcontainers
public class FindStoreInformationWithProductsIntegrationTest extends AbstractStoreIntegrationTest {

  @Autowired private StoreRepository storeRepository;

  @Test
  void findStore_whenValid_returnStoreWithProducts() {
    UUID storeId = UUID.fromString("9ee53be2-1474-4b22-ad8b-ed53746c8a28");
    List<UUID> productIds =
        List.of("9ee53be2-1474-4b22-ad8b-ed53746c8a30", "9ee53be2-1474-4b22-ad8b-ed53746c8a31")
            .stream()
            .map(stringUUID -> UUID.fromString(stringUUID))
            .toList();

    Store store = storeRepository.findStoreInformationWithProducts(storeId, productIds).get();

    assertNotNull(store);
    assertEquals(store.getProducts().size(), productIds.size());
    assertEquals(store.getId(), storeId);
    for (int i = 0; i < productIds.size(); i++) {
      assertEquals(productIds.get(i), store.getProducts().get(i).getId());
    }
  }
}
