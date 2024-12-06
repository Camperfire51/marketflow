package com.camperfire.marketflow.service;

import com.camperfire.marketflow.model.CustomerOrder;
import com.camperfire.marketflow.model.Invoice;
import com.camperfire.marketflow.repository.InvoiceRepository;
import com.camperfire.marketflow.service.email.EmailService;
import org.springframework.stereotype.Service;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final EmailService emailService;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, EmailService emailService) {
        this.invoiceRepository = invoiceRepository;
        this.emailService = emailService;
    }

    @Override
    public void createInvoice(CustomerOrder order) {

        Invoice invoice = Invoice.builder().build();

        emailService.sendInvoiceEmail("123123","123123", "123123"); //TODO: Actually implement invoice mail.

        invoiceRepository.save(invoice);
    }
}
