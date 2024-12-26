package com.camperfire.marketflow.service.email;

import com.camperfire.marketflow.dto.crud.email.EmailMessageRequest;
import com.camperfire.marketflow.model.EmailMessage;

public interface EmailService {

    void listen(EmailMessage emailMessage);

    void submit(EmailMessage emailMessage);

    EmailMessage createEmailMessage(EmailMessageRequest request);

    EmailMessage readEmailMessage(Long id);

    EmailMessage updateEmailMessage(EmailMessageRequest request);

    void deleteEmailMessage(Long id);
}
