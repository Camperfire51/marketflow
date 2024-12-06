package com.camperfire.marketflow.service.email;

public interface EmailService {

    public void sendVerificationEmail(String to, String subject, String body);

    void sendInvoiceEmail(String to, String subject, String body);
}
