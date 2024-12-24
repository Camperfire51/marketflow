package com.camperfire.marketflow.dto.mapper;

import com.camperfire.marketflow.dto.mapper.utility.InvoiceMapperUtility;
import com.camperfire.marketflow.dto.response.InvoiceResponse;
import com.camperfire.marketflow.model.Invoice;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {InvoiceMapperUtility.class})
public interface InvoiceMapper {
    Invoice toEntity(InvoiceRequest dto);

    InvoiceResponse toResponse(Invoice entity);

    List<InvoiceResponse> toResponseList(List<Invoice> entities);
}
