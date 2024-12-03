package com.camperfire.marketflow.repository;

import com.camperfire.marketflow.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {
}
