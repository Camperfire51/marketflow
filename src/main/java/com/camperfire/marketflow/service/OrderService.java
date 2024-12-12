package com.camperfire.marketflow.service;

import com.camperfire.marketflow.dto.request.OrderRequest;
import com.camperfire.marketflow.model.Cart;
import com.camperfire.marketflow.model.Order;

public interface OrderService {

    Order createOrder(OrderRequest orderRequest);

    Order readOrder(Long orderId);

    Order updateOrder(Long orderId, OrderRequest orderRequest);

    void deleteOrder(Long orderId);
}
