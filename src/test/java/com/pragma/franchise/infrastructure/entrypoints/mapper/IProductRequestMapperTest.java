package com.pragma.franchise.infrastructure.entrypoints.mapper;

import com.pragma.franchise.domain.model.Product;
import com.pragma.franchise.infrastructure.entrypoints.dto.request.ProductRequestDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class IProductRequestMapperTest {

    private final IProductRequestMapper productRequestMapper = Mappers.getMapper(IProductRequestMapper.class);

    @Test
    void toDomain_ShouldMapProductRequestDtoToProduct() {
        ProductRequestDto dto = new ProductRequestDto();
        dto.setName("Test Product");
        dto.setStock(10);
        dto.setBranchId(1L);

        Product product = productRequestMapper.toDomain(dto);

        assertThat(product).isNotNull();
        assertThat(product.getName()).isEqualTo(dto.getName());
        assertThat(product.getStock()).isEqualTo(dto.getStock());
        assertThat(product.getBranchId()).isEqualTo(dto.getBranchId());
    }
}
