package com.paypay.order.service.dataaccess.customer.repository;

import com.paypay.order.service.dataaccess.customer.entity.CustomerEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, UUID> {}
