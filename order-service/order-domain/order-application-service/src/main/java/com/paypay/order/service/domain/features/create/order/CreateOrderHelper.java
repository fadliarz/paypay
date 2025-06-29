package com.paypay.order.service.domain.features.create.order;

import com.paypay.order.service.domain.OrderDomainService;
import com.paypay.order.service.domain.entity.Customer;
import com.paypay.order.service.domain.entity.Order;
import com.paypay.order.service.domain.entity.Store;
import com.paypay.order.service.domain.event.OrderCreatedEvent;
import com.paypay.order.service.domain.exception.CustomerNotFoundException;
import com.paypay.order.service.domain.exception.StoreNotFoundException;
import com.paypay.order.service.domain.features.create.order.dto.CreateOrderCommand;
import com.paypay.order.service.domain.mapper.OrderDataMapper;
import com.paypay.order.service.domain.ports.output.client.StoreClient;
import com.paypay.order.service.domain.ports.output.repository.CustomerRepository;
import com.paypay.order.service.domain.ports.output.repository.OrderRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreateOrderHelper {

  private final OrderDataMapper orderDataMapper;
  private final StoreClient storeClient;
  private final OrderRepository orderRepository;
  private final OrderDomainService orderDomainService;
  private final CustomerRepository customerRepository;

  public CreateOrderHelper(
      OrderDataMapper orderDataMapper,
      StoreClient storeClient,
      OrderRepository orderRepository,
      OrderDomainService orderDomainService,
      CustomerRepository customerRepository) {
    this.orderDataMapper = orderDataMapper;
    this.storeClient = storeClient;
    this.orderRepository = orderRepository;
    this.orderDomainService = orderDomainService;
    this.customerRepository = customerRepository;
  }

  public OrderCreatedEvent persistOrder(CreateOrderCommand createOrderCommand) {
    checkCustomer(createOrderCommand.getCustomerId());
    Store store = checkStore(createOrderCommand);
    Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
    OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, store);
    saveOrder(order);
    return orderCreatedEvent;
  }

  private void checkCustomer(UUID customerId) {
    Optional<Customer> customer = customerRepository.findCustomer(customerId);
    if (customer.isEmpty()) throw new CustomerNotFoundException();
  }

  private Store checkStore(CreateOrderCommand createOrderCommand) {
    Store store = orderDataMapper.createOrderCommandToStore(createOrderCommand);
    Optional<Store> optionalStore =
        storeClient.findStoreInformationWithProducts(
            store.getId(), orderDataMapper.storeToProductIds(store));
    if (optionalStore.isEmpty()) throw new StoreNotFoundException();
    return optionalStore.get();
  }

  private void saveOrder(Order order) {
    Order orderResult = orderRepository.save(order);
    if (orderResult == null) throw new RuntimeException();
  }
}
