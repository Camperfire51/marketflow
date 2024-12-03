package com.camperfire.marketflow.repository.user;

import com.camperfire.marketflow.model.user.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
