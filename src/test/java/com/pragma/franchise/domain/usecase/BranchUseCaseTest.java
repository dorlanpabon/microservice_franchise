package com.pragma.franchise.domain.usecase;

import com.pragma.franchise.domain.api.IBranchPersistencePort;
import com.pragma.franchise.domain.model.Branch;
import com.pragma.franchise.domain.validator.BranchValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class BranchUseCaseTest {
    @Mock
    IBranchPersistencePort branchPersistencePort;
    @Mock
    BranchValidator branchValidator;
    @InjectMocks
    BranchUseCase branchUseCase;
    @Spy
    Branch branch;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        branch.setName("name");
        branch.setFranchiseId(1L);
    }

    @Test
    void testSaveBranch() {
        when(branchPersistencePort.save(any(Branch.class))).thenReturn(Mono.empty());
        when(branchValidator.validateFranchiseId(anyLong())).thenReturn(Mono.empty());
        when(branchValidator.validateFranchiseExists(anyLong())).thenReturn(Mono.empty());
        when(branchValidator.validateBranchNameLength(anyString())).thenReturn(Mono.empty());
        when(branchValidator.validateUniqueBranchNameAndFranchiseId(anyString(), anyLong())).thenReturn(Mono.empty());

        Mono<Void> result = branchUseCase.saveBranch(branch);

        StepVerifier.create(result)
                .verifyComplete();

        verify(branchPersistencePort, times(1)).save(any(Branch.class));
    }

    @Test
    void testUpdateBranchName() {
        when(branchPersistencePort.findById(anyLong())).thenReturn(Mono.just(branch));
        when(branchValidator.validateBranchName(anyString())).thenReturn(Mono.empty());
        when(branchValidator.validateUniqueBranchNameAndFranchiseId(anyString(), anyLong())).thenReturn(Mono.empty());
        when(branchPersistencePort.save(any(Branch.class))).thenReturn(Mono.empty());

        Mono<Void> result = branchUseCase.updateBranchName(1L, "newName");

        StepVerifier.create(result)
                .verifyComplete();

        verify(branchPersistencePort, times(1)).findById(anyLong());
        verify(branchPersistencePort, times(1)).save(any(Branch.class));
    }
}