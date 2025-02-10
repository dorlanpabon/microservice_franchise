package com.pragma.franchise.domain.validator;

import com.pragma.franchise.domain.api.IBranchPersistencePort;
import com.pragma.franchise.domain.api.IFranchisePersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class BranchValidatorTest {
    @Mock
    IBranchPersistencePort branchPersistencePort;
    @Mock
    IFranchisePersistencePort franchisePersistencePort;
    @InjectMocks
    BranchValidator branchValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidateFranchiseId() {
        Mono<Void> result = branchValidator.validateFranchiseId(1L);

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void testValidateFranchiseExists() {
        when(franchisePersistencePort.existsFranchise(anyLong())).thenReturn(Mono.just(true));

        Mono<Void> result = branchValidator.validateFranchiseExists(1L);

        StepVerifier.create(result)
                .verifyComplete();

        verify(franchisePersistencePort, times(1)).existsFranchise(anyLong());
    }

    @Test
    void testValidateBranchNameLength() {
        Mono<Void> result = branchValidator.validateBranchNameLength("name");

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void testValidateUniqueBranchName() {
        when(branchPersistencePort.existsBranchByName(anyString())).thenReturn(Mono.just(false));

        Mono<Void> result = branchValidator.validateUniqueBranchName("branchName");

        StepVerifier.create(result)
                .verifyComplete();

        verify(branchPersistencePort, times(1)).existsBranchByName(anyString());
    }

    @Test
    void testValidateUniqueBranchNameWhenBranchNameAlreadyExists() {
        when(branchPersistencePort.existsBranchByName(anyString())).thenReturn(Mono.just(true));

        Mono<Void> result = branchValidator.validateUniqueBranchName("branchName");

        StepVerifier.create(result)
                .verifyError();

        verify(branchPersistencePort, times(1)).existsBranchByName(anyString());
    }

    @Test
    void testValidateFranchiseExistsWhenFranchiseDoesNotExist() {
        when(franchisePersistencePort.existsFranchise(anyLong())).thenReturn(Mono.just(false));

        Mono<Void> result = branchValidator.validateFranchiseExists(1L);

        StepVerifier.create(result)
                .verifyError();

        verify(franchisePersistencePort, times(1)).existsFranchise(anyLong());
    }

    @Test
    void testValidateFranchiseIdWhenFranchiseIdIsNull() {
        Mono<Void> result = branchValidator.validateFranchiseId(null);

        StepVerifier.create(result)
                .verifyError();
    }

    @Test
    void testValidateBranchNameLengthWhenNameIsTooLong() {
        Mono<Void> result = branchValidator.validateBranchNameLength("name".repeat(100));

        StepVerifier.create(result)
                .verifyError();
    }

    @Test
    void testValidateBranchNameLengthWhenNameIsNull() {
        Mono<Void> result = branchValidator.validateBranchNameLength(null);

        StepVerifier.create(result)
                .verifyError();
    }
}