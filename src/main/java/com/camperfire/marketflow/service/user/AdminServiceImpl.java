package com.camperfire.marketflow.service.user;

import com.camperfire.marketflow.dto.mapper.CategoryMapper;
import com.camperfire.marketflow.dto.request.CategoryRequestDTO;
import com.camperfire.marketflow.model.Category;
import com.camperfire.marketflow.model.Product;
import com.camperfire.marketflow.model.ProductStatus;
import com.camperfire.marketflow.model.user.Admin;
import com.camperfire.marketflow.repository.CategoryRepository;
import com.camperfire.marketflow.service.AuthUserService;
import com.camperfire.marketflow.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final AuthUserService authUserService;
    private final ProductService productService;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Autowired
    public AdminServiceImpl(AuthUserService authUserService, ProductService productService, CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.authUserService = authUserService;
        this.productService = productService;
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Admin getAdmin(){
        return (Admin) authUserService.getAuthUser().getBaseUser();
    }

    @Override
    public Product getProduct(Long productId) {
        return productService.getProduct(productId);
    }

    @Override
    public List<Product> getProducts(String name, BigDecimal minPrice, BigDecimal maxPrice, String categoryPath, Long vendorId, ProductStatus status) {
        return productService.getProducts(name, minPrice, maxPrice, categoryPath, vendorId, status);
    }

    @Override
    public void setProductStatus(Long productId, ProductStatus status) {
        productService.setProductStatus(productId, status);
    }

    @Override
    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }

    @Override
    public Category createCategory(CategoryRequestDTO categoryRequestDTO) {

        Category category = categoryMapper.toEntity(categoryRequestDTO);

        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow();

        categoryRepository.delete(category);
    }
}
