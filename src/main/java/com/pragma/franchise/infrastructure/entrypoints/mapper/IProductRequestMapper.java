package com.pragma.franchise.infrastructure.entrypoints.mapper;

import com.pragma.franchise.domain.model.Product;
import com.pragma.franchise.infrastructure.entrypoints.dto.request.ProductRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface IProductRequestMapper {

    Product toDomain(ProductRequestDto productRequestDto);

}
