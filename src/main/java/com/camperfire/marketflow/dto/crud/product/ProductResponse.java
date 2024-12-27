package com.camperfire.marketflow.dto.crud.product;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {

    private Long id;
    private Long vendorId;
    private Long categoryId;
}
