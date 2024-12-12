package com.camperfire.marketflow.dto.mapper;

import com.camperfire.marketflow.dto.mapper.utility.ProductMapperUtility;
import com.camperfire.marketflow.dto.request.ProductRequestDTO;
import com.camperfire.marketflow.dto.response.ProductResponseDTO;
import com.camperfire.marketflow.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = ProductMapperUtility.class)
public interface ProductMapper {


    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "vendorId", source = "vendor.id")
    ProductRequestDTO toRequest(Product entity);

    @Mapping(target = "category", source = "categoryId", qualifiedByName = "mapCategory")
    @Mapping(target = "vendor", source = "vendorId", qualifiedByName = "mapVendor")
    Product toEntity(ProductRequestDTO dto);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "vendorId", source = "vendor.id")
    ProductResponseDTO toResponse(Product entity);

    List<ProductResponseDTO> toResponseList(List<Product> entities);

}
