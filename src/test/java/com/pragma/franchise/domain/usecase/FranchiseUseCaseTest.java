package com.pragma.franchise.domain.usecase;

import com.pragma.franchise.domain.api.IFranchisePersistencePort;
import com.pragma.franchise.domain.model.Franchise;
import com.pragma.franchise.domain.validator.FranchiseValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class FranchiseUseCaseTest {
    @Mock
    IFranchisePersistencePort franchisePersistencePort;
    @Mock
    FranchiseValidator franchiseValidator;
    @InjectMocks
    FranchiseUseCase franchiseUseCase;
    @Spy
    Franchise franchise;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        franchise.setName("name");
    }

    @Test
    void testSave() {
        when(franchisePersistencePort.save(any(Franchise.class))).thenReturn(Mono.empty());
        when(franchiseValidator.validateFranchiseName(anyString())).thenReturn(Mono.empty());
        when(franchiseValidator.validateUniqueFranchiseName(anyString())).thenReturn(Mono.empty());

        Mono<Void> result = franchiseUseCase.save(franchise);

        StepVerifier.create(result)
                .verifyComplete();

        verify(franchisePersistencePort, times(1)).save(any(Franchise.class));
    }
}

