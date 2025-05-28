package com.paypay.order.service.dataaccess.order.repository;

import com.paypay.order.service.dataaccess.order.entity.OrderEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity, UUID> {}
