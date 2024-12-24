package com.camperfire.marketflow.service.invoice;

import com.camperfire.marketflow.model.Order;
import com.camperfire.marketflow.model.Invoice;

public interface InvoiceService {

    Invoice createInvoice(InvoiceRequest invoiceRequest);

    Invoice readInvoice(Long id);

    Invoice updateInvoice(Long id, InvoiceRequest invoiceRequest);

    void deleteInvoice(Order order);

}
