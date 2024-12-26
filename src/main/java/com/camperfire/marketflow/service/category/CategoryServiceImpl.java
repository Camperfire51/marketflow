package com.camperfire.marketflow.service.category;

import com.camperfire.marketflow.dto.crud.category.CategoryRequest;
import com.camperfire.marketflow.dto.mapper.CategoryMapper;
import com.camperfire.marketflow.model.Category;
import com.camperfire.marketflow.repository.CategoryRepository;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository CategoryRepository, CategoryMapper CategoryMapper) {
        this.categoryRepository = CategoryRepository;
        this.categoryMapper = CategoryMapper;
    }

    @Override
    public Category createCategory(CategoryRequest request) {
        Category Category = categoryMapper.toEntity(request);
        return categoryRepository.save(Category);
    }

    @Override
    public Category readCategory(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category updateCategory(CategoryRequest request) {
        Category category = categoryRepository.findById(request.getId()).orElseThrow();

        //TODO: Implement update logic.

        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow();

        categoryRepository.delete(category);
    }
}

