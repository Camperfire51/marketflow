package com.camperfire.marketflow.service;

import com.camperfire.marketflow.model.Cart;
import com.camperfire.marketflow.model.CustomerOrder;
import com.camperfire.marketflow.model.user.Customer;
import org.springframework.stereotype.Service;

@Service
public class InvoiceServiceImpl implements InvoiceService {



    @Override
    public CustomerOrder order(Customer customer, Cart cart){
        return null;
    }
}
