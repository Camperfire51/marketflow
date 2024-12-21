package com.camperfire.marketflow.model.user;

import com.camperfire.marketflow.model.Address;
import com.camperfire.marketflow.model.AuthUser;
import com.camperfire.marketflow.model.Notification;
import com.camperfire.marketflow.model.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    protected LocalDateTime createdDate;

    @Embedded
    protected Address address;

    @Enumerated(EnumType.STRING)
    protected UserStatus status;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_user_id", referencedColumnName = "id")
    private AuthUser authUser;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Notification> notifications;

}
