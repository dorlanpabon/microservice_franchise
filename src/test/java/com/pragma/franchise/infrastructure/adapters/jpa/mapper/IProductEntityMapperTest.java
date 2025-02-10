package com.pragma.franchise.infrastructure.adapters.jpa.mapper;

import com.pragma.franchise.domain.model.Product;
import com.pragma.franchise.infrastructure.adapters.jpa.dto.ProductWithBranchNameDto;
import com.pragma.franchise.infrastructure.adapters.jpa.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class IProductEntityMapperTest {

    private final IProductEntityMapper productEntityMapper = Mappers.getMapper(IProductEntityMapper.class);

    @Test
    void toEntity_ShouldMapProductToProductEntity() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setStock(50);
        product.setBranchId(2L);

        ProductEntity productEntity = productEntityMapper.toEntity(product);

        assertThat(productEntity).isNotNull();
        assertThat(productEntity.getId()).isEqualTo(product.getId());
        assertThat(productEntity.getName()).isEqualTo(product.getName());
        assertThat(productEntity.getStock()).isEqualTo(product.getStock());
        assertThat(productEntity.getBranchId()).isEqualTo(product.getBranchId());
    }

    @Test
    void toDomain_ShouldMapProductEntityToProduct() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(1L);
        productEntity.setName("Phone");
        productEntity.setStock(100);
        productEntity.setBranchId(3L);

        Product product = productEntityMapper.toDomain(productEntity);

        assertThat(product).isNotNull();
        assertThat(product.getId()).isEqualTo(productEntity.getId());
        assertThat(product.getName()).isEqualTo(productEntity.getName());
        assertThat(product.getStock()).isEqualTo(productEntity.getStock());
        assertThat(product.getBranchId()).isEqualTo(productEntity.getBranchId());
    }

    @Test
    void toDomain_ShouldMapProductWithBranchNameDtoToProduct() {
        ProductWithBranchNameDto productWithBranchNameDto = new ProductWithBranchNameDto();
        productWithBranchNameDto.setId(1L);
        productWithBranchNameDto.setName("Phone");
        productWithBranchNameDto.setStock(100);
        productWithBranchNameDto.setBranchId(3L);
        productWithBranchNameDto.setBranchName("Branch");

        Product product = productEntityMapper.toDomain(productWithBranchNameDto);

        assertThat(product).isNotNull();
        assertThat(product.getId()).isEqualTo(productWithBranchNameDto.getId());
        assertThat(product.getName()).isEqualTo(productWithBranchNameDto.getName());
        assertThat(product.getStock()).isEqualTo(productWithBranchNameDto.getStock());
        assertThat(product.getBranchId()).isEqualTo(productWithBranchNameDto.getBranchId());
    }
}
