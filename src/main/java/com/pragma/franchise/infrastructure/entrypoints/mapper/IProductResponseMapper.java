package com.pragma.franchise.infrastructure.entrypoints.mapper;

import com.pragma.franchise.domain.model.Product;
import com.pragma.franchise.infrastructure.entrypoints.dto.response.ProductStockResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IProductResponseMapper {

    @Mapping(source = "id", target = "productId")
    @Mapping(source = "name", target = "productName")
    @Mapping(source = "stock", target = "stock")
    @Mapping(source = "branchId", target = "branchId")
    @Mapping(source = "branchName", target = "branchName")
    ProductStockResponseDto toResponse(Product product);
}
