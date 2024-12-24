package com.camperfire.marketflow.service.order;

import com.camperfire.marketflow.dto.mapper.OrderMapper;
import com.camperfire.marketflow.dto.crud.order.OrderRequest;
import com.camperfire.marketflow.model.Order;
import com.camperfire.marketflow.repository.OrderRepository;
import com.camperfire.marketflow.service.invoice.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Order createOrder(OrderRequest request) {
        Order order = orderMapper.toEntity(orderRequest);

        InvoiceRequest invoiceRequest = InvoiceRequest.builder().build();

        invoiceService.createInvoice(invoiceRequest);

        return orderRepository.save(order);
    }

    @Override
    public Order readOrder(OrderReadRequest request) {
        return orderRepository.findById(orderId).orElseThrow();
    }

    @Override
    public Order updateOrder(OrderUpdateRequest request) {
        Order order = orderMapper.toEntity(orderRequest);
        order.setId(orderId);
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(OrderDeleteRequest request) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        orderRepository.delete(order);
    }
}
