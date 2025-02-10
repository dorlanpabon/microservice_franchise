package com.pragma.franchise.domain.validator;

import com.pragma.franchise.domain.api.IFranchisePersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class FranchiseValidatorTest {
    @Mock
    IFranchisePersistencePort franchisePersistencePort;
    @InjectMocks
    FranchiseValidator franchiseValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidateFranchiseName() {
        Mono<Void> result = franchiseValidator.validateFranchiseName("name");

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void testValidateUniqueFranchiseName() {
        when(franchisePersistencePort.existsByName(anyString())).thenReturn(Mono.just(false));

        Mono<Void> result = franchiseValidator.validateUniqueFranchiseName("name");

        StepVerifier.create(result)
                .verifyComplete();

        verify(franchisePersistencePort, times(1)).existsByName(anyString());
    }

    @Test
    void testValidateUniqueFranchiseNameWhenNameExists() {
        when(franchisePersistencePort.existsByName(anyString())).thenReturn(Mono.just(true));

        Mono<Void> result = franchiseValidator.validateUniqueFranchiseName("name");

        StepVerifier.create(result)
                .verifyError();

        verify(franchisePersistencePort, times(1)).existsByName(anyString());
    }

    @Test
    void testValidateFranchiseNameWhenNameIsBlank() {
        Mono<Void> result = franchiseValidator.validateFranchiseName("");

        StepVerifier.create(result)
                .verifyError();

        verifyNoInteractions(franchisePersistencePort);
    }

    @Test
    void testValidateFranchiseNameWhenNameIsTooLong() {
        Mono<Void> result = franchiseValidator.validateFranchiseName("name".repeat(100));

        StepVerifier.create(result)
                .verifyError();

        verifyNoInteractions(franchisePersistencePort);
    }
}