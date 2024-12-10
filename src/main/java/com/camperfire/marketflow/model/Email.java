package com.camperfire.marketflow.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String recipient; // Email address of the recipient
    private String subject;   // Subject of the email
    @Column(length = 5000)
    private String body;

    private LocalDateTime sentAt;       // When the email was sent
    private LocalDateTime createdAt;   // When the email was created

    private EmailType type;
}
