package com.paypay.order.service.dataaccess.order.adapter;

import com.paypay.order.service.dataaccess.order.mapper.OrderDataAccessMapper;
import com.paypay.order.service.dataaccess.order.repository.OrderJpaRepository;
import com.paypay.order.service.domain.entity.Order;
import com.paypay.order.service.domain.ports.output.repository.OrderRepository;
import org.springframework.stereotype.Component;

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
}
