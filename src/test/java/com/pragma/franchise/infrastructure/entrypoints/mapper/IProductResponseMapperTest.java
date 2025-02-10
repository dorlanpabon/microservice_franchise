package com.pragma.franchise.infrastructure.entrypoints.mapper;

import com.pragma.franchise.domain.model.Product;
import com.pragma.franchise.infrastructure.entrypoints.dto.response.ProductStockResponseDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class IProductResponseMapperTest {

    private final IProductResponseMapper mapper = Mappers.getMapper(IProductResponseMapper.class);

    @Test
    void toResponse_ShouldMapProductToProductStockResponseDto() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product A");
        product.setStock(100);
        product.setBranchId(2L);
        product.setBranchName("Branch X");

        ProductStockResponseDto dto = mapper.toResponse(product);

        assertNotNull(dto);
        assertEquals(1L, dto.getProductId());
        assertEquals("Product A", dto.getProductName());
        assertEquals(100, dto.getStock());
        assertEquals(2L, dto.getBranchId());
        assertEquals("Branch X", dto.getBranchName());
    }
}
