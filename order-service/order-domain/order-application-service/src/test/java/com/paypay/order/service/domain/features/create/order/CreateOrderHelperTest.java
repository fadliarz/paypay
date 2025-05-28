package com.paypay.order.service.domain.features.create.order;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import com.paypay.order.service.domain.OrderDomainService;
import com.paypay.order.service.domain.entity.Customer;
import com.paypay.order.service.domain.entity.Order;
import com.paypay.order.service.domain.entity.Store;
import com.paypay.order.service.domain.event.OrderCreatedEvent;
import com.paypay.order.service.domain.exception.CustomerNotFoundException;
import com.paypay.order.service.domain.exception.StoreNotFoundException;
import com.paypay.order.service.domain.features.create.order.dto.CreateOrderCommand;
import com.paypay.order.service.domain.mapper.OrderDataMapper;
import com.paypay.order.service.domain.ports.output.repository.CustomerRepository;
import com.paypay.order.service.domain.ports.output.repository.OrderRepository;
import com.paypay.order.service.domain.ports.output.repository.StoreRepository;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateOrderHelperTest {
  @Mock private OrderDataMapper orderDataMapper;
  @Mock private CustomerRepository customerRepository;
  @Mock private StoreRepository storeRepository;
  @Mock private OrderRepository orderRepository;
  @Mock private OrderDomainService orderDomainService;
  @InjectMocks private CreateOrderHelper createOrderHelper;

  private static final UUID CUSTOMER_ID = UUID.randomUUID();
  private static final UUID STORE_ID = UUID.randomUUID();

  private CreateOrderCommand mockedCreateOrderCommand;
  private Customer mockedCustomer;
  private Store mockedStore;
  private Order mockedOrder;
  private OrderCreatedEvent mockedOrderCreatedEvent;

  @BeforeEach
  void setUp() {
    mockedCreateOrderCommand =
        CreateOrderCommand.builder().customerId(CUSTOMER_ID).storeId(STORE_ID).build();
    mockedCustomer = Customer.Builder.builder().setId(CUSTOMER_ID).build();
    mockedOrderCreatedEvent = new OrderCreatedEvent(mockedOrder, ZonedDateTime.now());
    mockedStore = Store.Builder.builder().build();
    mockedOrder = Order.Builder.builder().build();
    when(customerRepository.findCustomer(CUSTOMER_ID))
        .thenReturn(Optional.ofNullable(mockedCustomer));
    lenient()
        .when(orderDataMapper.createOrderCommandToStore(mockedCreateOrderCommand))
        .thenReturn(mockedStore);
    lenient()
        .when(storeRepository.findStoreInformation(mockedStore))
        .thenReturn(Optional.ofNullable(mockedStore));
    lenient()
        .when(orderDataMapper.createOrderCommandToOrder(mockedCreateOrderCommand))
        .thenReturn(mockedOrder);
    lenient()
        .when(orderDomainService.validateAndInitiateOrder(mockedOrder, mockedStore))
        .thenReturn(mockedOrderCreatedEvent);
    lenient().when(orderRepository.save(mockedOrder)).thenReturn(mockedOrder);
  }

  @Test
  void testPersistOrder_ShouldCreateOrderSuccessfully() {
    OrderCreatedEvent result = createOrderHelper.persistOrder(mockedCreateOrderCommand);

    assertNotNull(result);
    assertEquals(mockedOrderCreatedEvent, result);
  }

  @Test
  void testPersistOrder_CustomerNotFound_throwsCustomerNotFoundException() {
    when(customerRepository.findCustomer(CUSTOMER_ID)).thenReturn(Optional.empty());

    assertThrows(
        CustomerNotFoundException.class,
        () -> createOrderHelper.persistOrder(mockedCreateOrderCommand));
  }

  @Test
  void testPersistOrder_StoreNotFound_throwsStoreNotFoundException() {
    when(storeRepository.findStoreInformation(mockedStore)).thenReturn(Optional.empty());

    assertThrows(
        StoreNotFoundException.class,
        () -> createOrderHelper.persistOrder(mockedCreateOrderCommand));
  }

  @Test
  void testPersistOrder_SaveOrderReturnsNull_throwsRuntimeExecption() {
    when(orderRepository.save(mockedOrder)).thenReturn(null);

    assertThrows(
        RuntimeException.class, () -> createOrderHelper.persistOrder(mockedCreateOrderCommand));
  }
}
