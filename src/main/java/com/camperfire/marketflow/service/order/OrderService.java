package com.camperfire.marketflow.service.order;

import com.camperfire.marketflow.dto.crud.order.OrderRequest;
import com.camperfire.marketflow.model.Order;

public interface OrderService {

    Order createOrder(OrderRequest request);

    Order readOrder(OrderReadRequest request);

    Order updateOrder(OrderUpdateRequest request);

    void deleteOrder(OrderDeleteRequest request);
}
