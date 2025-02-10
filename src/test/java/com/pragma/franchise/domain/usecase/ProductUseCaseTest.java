package com.pragma.franchise.domain.usecase;

import com.pragma.franchise.domain.api.IProductPersistencePort;
import com.pragma.franchise.domain.model.Product;
import com.pragma.franchise.domain.validator.ProductValidator;
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

class ProductUseCaseTest {
    @Mock
    IProductPersistencePort productPersistencePort;
    @Mock
    ProductValidator productValidator;
    @InjectMocks
    ProductUseCase productUseCase;
    @Spy
    Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        product.setName("name");
        product.setStock(10);
        product.setBranchId(1L);
    }

    @Test
    void testSaveProduct() {
        when(productPersistencePort.save(any(Product.class))).thenReturn(Mono.empty());
        when(productValidator.validateBranchExists(anyLong())).thenReturn(Mono.empty());
        when(productValidator.validateProductName(anyString())).thenReturn(Mono.empty());
        when(productValidator.validateStock(anyInt())).thenReturn(Mono.empty());
        when(productValidator.validateUniqueProductName(anyString(), anyLong())).thenReturn(Mono.empty());

        Mono<Void> result = productUseCase.saveProduct(product);

        StepVerifier.create(result)
                .expectComplete()
                .verify();

        verify(productPersistencePort, times(1)).save(any(Product.class));
    }
}