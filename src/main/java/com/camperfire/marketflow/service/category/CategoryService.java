package com.camperfire.marketflow.service.category;

import com.camperfire.marketflow.dto.crud.category.CategoryRequest;
import com.camperfire.marketflow.model.Category;

public interface CategoryService {

    Category createCategory(CategoryRequest request);

    Category readCategory(CategoryReadRequest request);

    Category updateCategory(CategoryUpdateRequest request);

    void deleteCategory(CategoryDeleteRequest request);
}
