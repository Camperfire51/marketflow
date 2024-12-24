package com.camperfire.marketflow.dto.mapper;

import com.camperfire.marketflow.dto.crud.category.CategoryRequest;
import com.camperfire.marketflow.dto.response.CategoryResponseDTO;
import com.camperfire.marketflow.model.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity(CategoryRequest request);

    CategoryResponseDTO toResponse(Category entity);

    List<CategoryResponseDTO> toResponseList(List<Category> entities);
}
