package com.pragma.franchise.infrastructure.entrypoints;

import com.pragma.franchise.infrastructure.entrypoints.dto.request.ProductRequestDto;
import com.pragma.franchise.infrastructure.entrypoints.handler.interfaces.IProductHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class ProductControllerTest {
    @Mock
    IProductHandler productHandler;
    @InjectMocks
    ProductController productController;
    @Spy
    ProductRequestDto productRequestDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productRequestDto.setName("product");
        productRequestDto.setStock(10);
        productRequestDto.setBranchId(1L);
    }

    @Test
    void testAddProduct() {
        when(productHandler.addProduct(any(ProductRequestDto.class))).thenReturn(Mono.empty());

        Mono<ResponseEntity<Void>> result = productController.addProduct(productRequestDto);

        StepVerifier.create(result)
                .expectNext(ResponseEntity.status(201).build())
                .verifyComplete();

        verify(productHandler, times(1)).addProduct(any(ProductRequestDto.class));
    }

    @Test
    void testDeleteProduct() {
        when(productHandler.deleteProduct(anyLong(), anyLong())).thenReturn(Mono.empty());

        Mono<ResponseEntity<Void>> result = productController.deleteProduct(1L, 1L);

        StepVerifier.create(result)
                .expectNext(ResponseEntity.status(204).build())
                .verifyComplete();

        verify(productHandler, times(1)).deleteProduct(anyLong(), anyLong());
    }
}