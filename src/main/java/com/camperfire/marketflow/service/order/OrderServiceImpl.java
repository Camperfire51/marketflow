package com.camperfire.marketflow.service.order;

import com.camperfire.marketflow.dto.crud.invoice.InvoiceRequest;
import com.camperfire.marketflow.dto.mapper.OrderMapper;
import com.camperfire.marketflow.dto.crud.order.OrderRequest;
import com.camperfire.marketflow.model.EmailMessage;
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
        Order order = orderMapper.toEntity(request);

        InvoiceRequest invoiceRequest = InvoiceRequest.builder().build();

        invoiceService.createInvoice(invoiceRequest);

        return orderRepository.save(order);
    }

    @Override
    public Order readOrder(Long id) {
        return orderRepository.findById(id).orElseThrow();
    }

    @Override
    public Order updateOrder(OrderRequest request) {
        Order order = orderRepository.findById(request.getId()).orElseThrow();

        //TODO: Implement update logic.

        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow();

        orderRepository.delete(order);
    }
}
