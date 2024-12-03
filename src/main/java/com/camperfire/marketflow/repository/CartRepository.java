package com.camperfire.marketflow.repository;

import com.camperfire.marketflow.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
