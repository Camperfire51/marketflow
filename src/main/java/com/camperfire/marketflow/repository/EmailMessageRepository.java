package com.camperfire.marketflow.repository;

import com.camperfire.marketflow.model.EmailMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailMessageRepository extends JpaRepository<EmailMessage, Long> {
}
