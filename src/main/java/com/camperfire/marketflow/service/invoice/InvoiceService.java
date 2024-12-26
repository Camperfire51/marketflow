package com.camperfire.marketflow.service.invoice;

import com.camperfire.marketflow.dto.crud.invoice.InvoiceRequest;
import com.camperfire.marketflow.model.Invoice;

public interface InvoiceService {

    Invoice createInvoice(InvoiceRequest invoiceRequest);

    Invoice readInvoice(Long id);

    Invoice updateInvoice(InvoiceRequest invoiceRequest);

    void deleteInvoice(Long id);

}
