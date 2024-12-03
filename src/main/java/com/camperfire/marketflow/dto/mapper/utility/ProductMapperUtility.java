package com.camperfire.marketflow.dto.mapper.utility;

import com.camperfire.marketflow.model.Category;
import com.camperfire.marketflow.model.user.Vendor;
import com.camperfire.marketflow.repository.CategoryRepository;
import com.camperfire.marketflow.repository.user.VendorRepository;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class ProductMapperUtility {

    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;


    public ProductMapperUtility(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Named("mapCategory")
    public Category mapCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Named("mapVendor")
    public Vendor mapVendor(Long vendorId) {
        return vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
    }
}
