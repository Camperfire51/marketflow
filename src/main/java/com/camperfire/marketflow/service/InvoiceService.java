package com.camperfire.marketflow.service;

import com.camperfire.marketflow.model.CustomerOrder;

public interface InvoiceService {

    void createInvoice(CustomerOrder order);

}
