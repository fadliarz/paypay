package com.paypay.order.service.dataaccess.order.adapter;

import com.paypay.order.service.dataaccess.order.mapper.OrderDataAccessMapper;
import com.paypay.order.service.dataaccess.order.repository.OrderJpaRepository;
import com.paypay.order.service.domain.entity.Order;
import com.paypay.order.service.domain.ports.output.repository.OrderRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OrderRepositoryImpl implements OrderRepository {

  private final OrderDataAccessMapper orderDataAccessMapper;
  private final OrderJpaRepository orderJpaRepository;

  public OrderRepositoryImpl(
      OrderDataAccessMapper orderDataAccessMapper, OrderJpaRepository orderJpaRepository) {
    this.orderDataAccessMapper = orderDataAccessMapper;
    this.orderJpaRepository = orderJpaRepository;
  }

  @Override
  public Order save(Order order) {
    return orderDataAccessMapper.orderEntityToOrder(
        orderJpaRepository.save(orderDataAccessMapper.orderToOrderEntity(order)));
  }

  @Override
  @Transactional
  public Optional<Order> findOrder(UUID orderId) {
    return orderJpaRepository.findById(orderId).map(orderDataAccessMapper::orderEntityToOrder);
  }
}
