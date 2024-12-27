package com.camperfire.marketflow.service.order;

import com.camperfire.marketflow.dto.crud.order.OrderRequest;
import com.camperfire.marketflow.model.Order;

public interface OrderService {

    Order submitOrder();

    Order createOrder(OrderRequest request);

    Order readOrder(Long id);

    Order updateOrder(OrderRequest request);

    void deleteOrder(Long id);
}
