package com.camperfire.marketflow.service;

import com.camperfire.marketflow.model.Cart;
import com.camperfire.marketflow.model.CustomerOrder;
import com.camperfire.marketflow.model.OrderStatus;
import com.camperfire.marketflow.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderServiceImpl {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public CustomerOrder submitOrder(Cart cart) {
        CustomerOrder customerOrder = CustomerOrder.builder()
                .customer(cart.getCustomer())
                .products(cart.getProducts())
                .orderDate(LocalDateTime.now())
                .status(OrderStatus.PENDING)
                .shippingAddress(cart.getCustomer().getAddress())
                .build();

        return orderRepository.save(customerOrder);
    }
}
