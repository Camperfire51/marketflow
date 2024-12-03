package com.camperfire.marketflow.repository;

import com.camperfire.marketflow.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {

    AuthUser findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    AuthUser findByVerificationToken(String verificationToken);
}
