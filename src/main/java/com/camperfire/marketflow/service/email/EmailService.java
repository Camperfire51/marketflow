package com.camperfire.marketflow.service.email;

import com.camperfire.marketflow.model.EmailMessage;

public interface EmailService {

    void listen(EmailMessage emailMessage);

    void submit(EmailMessage emailMessage);
}
