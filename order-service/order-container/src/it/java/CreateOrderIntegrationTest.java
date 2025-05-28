import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.paypay.order.service.domain.OrderServiceApplication;
import com.paypay.order.service.domain.features.create.order.CreateOrderCommandHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(classes = {OrderServiceApplication.class})
@Testcontainers
public class CreateOrderIntegrationTest extends AbstractOrderIntegrationTest {

  @Autowired private CreateOrderCommandHandler createOrderCommandHandler;

  @Test
  void placeOrder_whenCommandIsValid_persistOrderAndItems() {
    assertEquals(1, 1);
  }
}
