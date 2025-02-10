package com.pragma.franchise.infrastructure.adapters.jpa.mapper;

import com.pragma.franchise.domain.model.Product;
import com.pragma.franchise.infrastructure.adapters.jpa.dto.ProductWithBranchNameDto;
import com.pragma.franchise.infrastructure.adapters.jpa.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface IProductEntityMapper {
    ProductEntity toEntity(Product product);
    Product toDomain(ProductEntity productEntity);
    Product toDomain(ProductWithBranchNameDto productEntity);
}
