package com.camperfire.marketflow.service;

import com.camperfire.marketflow.dto.mapper.OrderMapper;
import com.camperfire.marketflow.dto.request.InvoiceRequest;
import com.camperfire.marketflow.dto.request.OrderRequest;
import com.camperfire.marketflow.model.Cart;
import com.camperfire.marketflow.model.Order;
import com.camperfire.marketflow.model.OrderStatus;
import com.camperfire.marketflow.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final InvoiceService invoiceService;

    @Autowired
    public OrderServiceImpl(OrderMapper orderMapper, OrderRepository orderRepository, InvoiceService invoiceService) {
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
        this.invoiceService = invoiceService;
    }

    @Override
    public Order createOrder(OrderRequest orderRequest) {
        Order order = orderMapper.toEntity(orderRequest);

        InvoiceRequest invoiceRequest = InvoiceRequest.builder().build();

        invoiceService.createInvoice(invoiceRequest);

        return orderRepository.save(order);
    }

    @Override
    public Order readOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow();
    }

    @Override
    public Order updateOrder(Long orderId, OrderRequest orderRequest) {
        Order order = orderMapper.toEntity(orderRequest);
        order.setId(orderId);
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        orderRepository.delete(order);
    }
}
