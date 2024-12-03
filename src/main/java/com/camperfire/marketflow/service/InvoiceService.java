package com.camperfire.marketflow.service;

import com.camperfire.marketflow.model.Cart;
import com.camperfire.marketflow.model.CustomerOrder;
import com.camperfire.marketflow.model.user.Customer;

public interface InvoiceService {
    CustomerOrder order(Customer customer, Cart cart);
}
