package com.camperfire.marketflow.service;

import com.camperfire.marketflow.dto.request.InvoiceRequest;
import com.camperfire.marketflow.model.Order;
import com.camperfire.marketflow.model.Invoice;

public interface InvoiceService {

    Invoice createInvoice(InvoiceRequest invoiceRequest);

    Invoice readInvoice(Long id);

    Invoice updateInvoice(Long id, InvoiceRequest invoiceRequest);

    void deleteInvoice(Order order);

}
