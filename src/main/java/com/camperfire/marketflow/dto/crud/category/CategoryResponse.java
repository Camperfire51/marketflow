package com.camperfire.marketflow.dto.crud.category;

import com.camperfire.marketflow.dto.crud.product.ProductResponse;
import com.camperfire.marketflow.model.Product;
import lombok.*;
import org.mapstruct.Mapping;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {
    private Long id;
    private Long categoryId;
    private Long vendorId;
}
