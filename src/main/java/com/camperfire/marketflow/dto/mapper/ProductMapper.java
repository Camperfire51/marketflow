package com.camperfire.marketflow.dto.mapper;

import com.camperfire.marketflow.dto.crud.product.ProductRequest;
import com.camperfire.marketflow.dto.crud.product.ProductResponse;
import com.camperfire.marketflow.dto.mapper.utility.ProductMapperUtility;
import com.camperfire.marketflow.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = ProductMapperUtility.class)
public interface ProductMapper {


    @Mapping(target = "categoryId", source = "category.id")
    ProductRequest toRequest(Product entity);

    @Mapping(target = "category", source = "categoryId", qualifiedByName = "mapCategory")
    Product toEntity(ProductRequest dto);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "vendorId", source = "vendor.id")
    ProductResponse toResponse(Product entity);

    List<ProductResponse> toResponseList(List<Product> entities);

}
