package com.camperfire.marketflow.service.category;

import com.camperfire.marketflow.dto.crud.category.CategoryRequest;
import com.camperfire.marketflow.model.Category;

public interface CategoryService {

    Category createCategory(CategoryRequest request);

    Category readCategory(Long id);

    Category updateCategory(CategoryRequest request);

    void deleteCategory(Long id);
}
