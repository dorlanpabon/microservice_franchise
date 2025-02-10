package com.pragma.franchise.domain.validator;

import com.pragma.franchise.domain.api.IBranchPersistencePort;
import com.pragma.franchise.domain.api.IProductPersistencePort;
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

class ProductValidatorTest {
    @Mock
    IBranchPersistencePort branchPersistencePort;
    @Mock
    IProductPersistencePort productPersistencePort;
    @InjectMocks
    ProductValidator productValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidateBranchExists() {
        when(branchPersistencePort.existsBranchById(anyLong())).thenReturn(Mono.just(true));

        Mono<Void> result = productValidator.validateBranchExists(Long.valueOf(1));

        StepVerifier.create(result)
                .expectComplete()
                .verify();

        verify(branchPersistencePort, times(1)).existsBranchById(anyLong());
    }

    @Test
    void testValidateProductName() {
        Mono<Void> result = productValidator.validateProductName("name");

        StepVerifier.create(result)
                .expectComplete()
                .verify();
    }

    @Test
    void testValidateStock() {
        Mono<Void> result = productValidator.validateStock(0);

        StepVerifier.create(result)
                .expectComplete()
                .verify();
    }

    @Test
    void testValidateStockInvalid() {
        Mono<Void> result = productValidator.validateStock(-1);

        StepVerifier.create(result)
                .expectError()
                .verify();
    }

    @Test
    void testValidateUniqueProductName() {
        when(productPersistencePort.existsProductByNameAndBranchId(anyString(), anyLong())).thenReturn(Mono.just(false));

        Mono<Void> result = productValidator.validateUniqueProductName("name", 1L);

        StepVerifier.create(result)
                .expectComplete()
                .verify();

        verify(productPersistencePort, times(1)).existsProductByNameAndBranchId(anyString(), anyLong());
    }

    @Test
    void testValidateUniqueProductNameAlreadyExists() {
        when(productPersistencePort.existsProductByNameAndBranchId(anyString(), anyLong())).thenReturn(Mono.just(true));

        Mono<Void> result = productValidator.validateUniqueProductName("name", 1L);

        StepVerifier.create(result)
                .expectError()
                .verify();

        verify(productPersistencePort, times(1)).existsProductByNameAndBranchId(anyString(), anyLong());
    }

    @Test
    void testValidateProductExists() {
        when(productPersistencePort.existsByIdAndBranchId(anyLong(), anyLong())).thenReturn(Mono.just(true));

        Mono<Void> result = productValidator.validateProductExists(1L, 1L);

        StepVerifier.create(result)
                .expectComplete()
                .verify();

        verify(productPersistencePort, times(1)).existsByIdAndBranchId(anyLong(), anyLong());
    }

    @Test
    void testValidateProductNotExists() {
        when(productPersistencePort.existsByIdAndBranchId(anyLong(), anyLong())).thenReturn(Mono.just(false));

        Mono<Void> result = productValidator.validateProductExists(1L, 1L);

        StepVerifier.create(result)
                .expectError()
                .verify();

        verify(productPersistencePort, times(1)).existsByIdAndBranchId(anyLong(), anyLong());
    }
}