package com.camperfire.marketflow.repository.user;

import com.camperfire.marketflow.model.user.Customer;
import com.camperfire.marketflow.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE c.authUser.username = :username")
    Optional<Customer> findByUserAuthUsername(@Param("username") String username);

    @Query("SELECT c FROM Customer c WHERE c.authUser.username = :username")
    Optional<Customer> findByUserAuthUserRole(@Param("user-role") UserRole username);

}
