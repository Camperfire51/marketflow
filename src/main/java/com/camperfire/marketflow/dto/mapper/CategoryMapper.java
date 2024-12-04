package com.camperfire.marketflow.dto.mapper;

import com.camperfire.marketflow.dto.request.CategoryRequestDTO;
import com.camperfire.marketflow.dto.response.CategoryResponseDTO;
import com.camperfire.marketflow.dto.response.ProductResponseDTO;
import com.camperfire.marketflow.model.Category;
import com.camperfire.marketflow.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity(CategoryRequestDTO categoryRequestDTO);

    CategoryResponseDTO toResponse(Category category);

    List<CategoryResponseDTO> toResponseList(List<Category> entities);
}
