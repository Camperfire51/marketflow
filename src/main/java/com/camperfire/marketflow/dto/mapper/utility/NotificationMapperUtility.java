package com.camperfire.marketflow.dto.mapper.utility;

import com.camperfire.marketflow.repository.user.AdminRepository;
import com.camperfire.marketflow.repository.user.CustomerRepository;
import com.camperfire.marketflow.repository.user.VendorRepository;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapperUtility {

    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;

    public NotificationMapperUtility(AdminRepository adminRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.adminRepository = adminRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }
}
