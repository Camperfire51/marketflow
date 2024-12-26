package com.camperfire.marketflow.service.user;

import com.camperfire.marketflow.dto.crud.user.UserRequest;
import com.camperfire.marketflow.exception.NotImplementedException;
import com.camperfire.marketflow.model.Cart;
import com.camperfire.marketflow.model.EmailMessage;
import com.camperfire.marketflow.model.UserStatus;
import com.camperfire.marketflow.model.user.Admin;
import com.camperfire.marketflow.model.user.Customer;
import com.camperfire.marketflow.model.user.User;
import com.camperfire.marketflow.model.user.Vendor;
import com.camperfire.marketflow.repository.user.AdminRepository;
import com.camperfire.marketflow.repository.user.CustomerRepository;
import com.camperfire.marketflow.repository.user.VendorRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;

    public UserServiceImpl(AdminRepository adminRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.adminRepository = adminRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public User createUser(UserRequest request) {
        switch (request.getRole()) {
            case ROLE_CUSTOMER -> {
                Customer customer = Customer.builder()
                        .address(request.getAddress())
                        .cart(Cart.builder().build())
                        .status(UserStatus.APPROVED)
                        .build();

                return customerRepository.save(customer);
            }
            case ROLE_VENDOR -> {
                Vendor vendor = Vendor.builder()
                        .address(request.getAddress())
                        .status(UserStatus.APPROVED)
                        .build();

                return vendorRepository.save(vendor);
            }

            case ROLE_ADMIN -> {
                Admin admin = Admin.builder()
                        .address(request.getAddress())
                        .status(UserStatus.APPROVED)
                        .build();

                return adminRepository.save(admin);
            }
        }

        return null;
    }

    @Override
    public User readUser(Long id) {
        //TODO: Implement read user logic.
        throw new UnsupportedOperationException();
    }

    @Override
    public User updateUser(UserRequest request) {
        //TODO: Implement update user logic.
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteUser(Long id) {
        //TODO: Implement delete user logic.
        throw new UnsupportedOperationException();
    }
}
