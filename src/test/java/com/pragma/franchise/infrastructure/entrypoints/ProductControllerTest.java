package com.pragma.franchise.infrastructure.entrypoints;

import com.pragma.franchise.infrastructure.entrypoints.dto.request.ProductRequestDto;
import com.pragma.franchise.infrastructure.entrypoints.dto.request.ProductStockUpdateDto;
import com.pragma.franchise.infrastructure.entrypoints.dto.response.ProductStockResponseDto;
import com.pragma.franchise.infrastructure.entrypoints.handler.interfaces.IProductHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
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
    @Spy
    ProductStockUpdateDto productStockUpdateDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productRequestDto.setName("product");
        productRequestDto.setStock(10);
        productRequestDto.setBranchId(1L);

        productStockUpdateDto.setStockChange(10);
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

    @Test
    void testUpdateStock() {
        when(productHandler.updateStock(anyLong(), anyLong(), any())).thenReturn(Mono.empty());

        Mono<ResponseEntity<Void>> result = productController.updateStock(1L, 1L, productStockUpdateDto);

        StepVerifier.create(result)
                .expectNext(ResponseEntity.status(204).build())
                .verifyComplete();

        verify(productHandler, times(1)).updateStock(anyLong(), anyLong(), any());
    }

    @Test
    void testGetMaxStockProductByBranchForFranchise() {
        when(productHandler.getMaxStockProductByBranchForFranchise(anyLong())).thenReturn(null);

        ResponseEntity<Flux<ProductStockResponseDto>> result = productController.getMaxStockProductByBranchForFranchise(1L);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertNull(result.getBody());

        verify(productHandler, times(1)).getMaxStockProductByBranchForFranchise(anyLong());
    }
}