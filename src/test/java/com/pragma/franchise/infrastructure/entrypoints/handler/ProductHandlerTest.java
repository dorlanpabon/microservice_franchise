package com.pragma.franchise.infrastructure.entrypoints.handler;

import com.pragma.franchise.domain.model.Product;
import com.pragma.franchise.domain.spi.IProductServicePort;
import com.pragma.franchise.infrastructure.entrypoints.dto.request.ProductRequestDto;
import com.pragma.franchise.infrastructure.entrypoints.mapper.IProductRequestMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class ProductHandlerTest {
    @Mock
    IProductServicePort productServicePort;
    @Mock
    IProductRequestMapper productRequestMapper;
    @InjectMocks
    ProductHandler productHandler;
    @Spy
    ProductRequestDto productRequestDto;
    @Spy
    Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productRequestDto.setName("product");
        productRequestDto.setStock(10);
        productRequestDto.setBranchId(1L);

        product.setName("product");
        product.setStock(10);
        product.setBranchId(1L);
    }

    @Test
    void testAddProduct() {
        when(productRequestMapper.toDomain(any(ProductRequestDto.class))).thenReturn(product);
        when(productServicePort.saveProduct(any(Product.class))).thenReturn(Mono.empty());

        Mono<Void> result = productHandler.addProduct(productRequestDto);

        StepVerifier.create(result)
                .verifyComplete();

        verify(productRequestMapper, times(1)).toDomain(any(ProductRequestDto.class));
    }

    @Test
    void testDeleteProduct() {
        when(productServicePort.deleteProduct(anyLong(), anyLong())).thenReturn(Mono.empty());

        Mono<Void> result = productHandler.deleteProduct(1L, 1L);

        StepVerifier.create(result)
                .verifyComplete();
    }
}

