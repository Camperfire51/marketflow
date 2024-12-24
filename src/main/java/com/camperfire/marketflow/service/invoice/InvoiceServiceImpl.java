package com.camperfire.marketflow.service.invoice;

import com.camperfire.marketflow.dto.mapper.InvoiceMapper;
import com.camperfire.marketflow.model.EmailMessage;
import com.camperfire.marketflow.model.Order;
import com.camperfire.marketflow.model.Invoice;
import com.camperfire.marketflow.repository.InvoiceRepository;
import com.camperfire.marketflow.service.email.EmailService;
import org.springframework.stereotype.Service;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceMapper invoiceMapper;
    private final InvoiceRepository invoiceRepository;
    private final EmailService emailService;

    public InvoiceServiceImpl(InvoiceMapper invoiceMapper, InvoiceRepository invoiceRepository, EmailService emailService) {
        this.invoiceMapper = invoiceMapper;
        this.invoiceRepository = invoiceRepository;
        this.emailService = emailService;
    }

    @Override
    public Invoice createInvoice(InvoiceRequest invoiceRequest){
        Invoice invoice = invoiceMapper.toEntity(invoiceRequest);

        EmailMessage emailMessage = EmailMessage.builder().build();

        emailService.submit(emailMessage);

        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice readInvoice(Long id){
        return invoiceRepository.findById(id).orElseThrow();
    }

    @Override
    public Invoice updateInvoice(Long id, InvoiceRequest invoiceRequest){
        Invoice invoice = invoiceMapper.toEntity(invoiceRequest);
        invoice.setId(id);
        return invoiceRepository.save(invoice);
    }

    @Override
    public void deleteInvoice(Order order){
        invoiceRepository.deleteById(order.getId());
    }
}
