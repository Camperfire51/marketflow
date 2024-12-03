package com.camperfire.marketflow.model.user;

import com.camperfire.marketflow.model.Address;
import com.camperfire.marketflow.model.AuthUser;
import com.camperfire.marketflow.model.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class BaseUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected LocalDateTime createdDate;

    @Embedded
    protected Address address;

    @Enumerated(EnumType.STRING)
    protected UserStatus status;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_user_id", referencedColumnName = "id")
    private AuthUser authUser;
}
