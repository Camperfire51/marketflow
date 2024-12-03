//package com.camperfire.marketflow.dto.mapper;
//
//import com.camperfire.marketflow.dto.OrderDTO;
//import com.camperfire.marketflow.dto.mapper.utility.OrderMapperUtility;
//import com.camperfire.marketflow.dto.request.ProductRequestDTO;
//import com.camperfire.marketflow.dto.response.ProductResponseDTO;
//import com.camperfire.marketflow.model.Order;
//import com.camperfire.marketflow.model.Product;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//
//import java.util.List;
//
//@Mapper(componentModel = "spring", uses = {OrderMapperUtility.class})
//public interface OrderMapper {
//
//    @Mapping(target = "category", source = "categoryId", qualifiedByName = "mapCategory")
//    @Mapping(target = "vendor", source = "vendorId", qualifiedByName = "mapVendor")
//    Order toEntity(OrderDTO orderDTO);
//
//    @Mapping(target = "categoryId", source = "category.id")
//    @Mapping(target = "vendorId", source = "vendor.id")
//    ProductResponseDTO toDTO(Product entity);
//
//    List<ProductResponseDTO> toResponseList(List<Product> entities);
//
//
//}
