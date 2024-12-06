package com.camperfire.marketflow.service;

import com.camperfire.marketflow.model.Cart;
import com.camperfire.marketflow.model.CustomerOrder;

public interface OrderService {

    CustomerOrder order(Cart order);
}
