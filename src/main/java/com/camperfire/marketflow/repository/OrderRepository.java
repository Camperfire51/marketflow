package com.camperfire.marketflow.repository;

import com.camperfire.marketflow.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
