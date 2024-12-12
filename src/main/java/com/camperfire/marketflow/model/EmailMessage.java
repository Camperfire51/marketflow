package com.camperfire.marketflow.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email ;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotBlank
    @Column(nullable = false)
    private String from;

    @Email
    @NotBlank
    @Column(nullable = false)
    private String replyTo;

    @Email
    @NotBlank
    @Column(nullable = false)
    private String to;

    @NotBlank
    @Size(max = 255)
    @Column(nullable = false)
    private String subject;

    @NotBlank
    @Size(max = 5000)
    @Column(nullable = false, length = 5000)
    private String text;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime sentDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmailType type;
}
