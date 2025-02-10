package com.pragma.franchise.infrastructure.entrypoints.handler;

import com.pragma.franchise.domain.model.Branch;
import com.pragma.franchise.domain.spi.IBranchServicePort;
import com.pragma.franchise.infrastructure.entrypoints.dto.request.BranchRequestDto;
import com.pragma.franchise.infrastructure.entrypoints.mapper.IBranchRequestMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class BranchHandlerTest {
    @Mock
    IBranchServicePort branchServicePort;
    @Mock
    IBranchRequestMapper branchRequestMapper;
    @InjectMocks
    BranchHandler branchHandler;
    @Spy
    BranchRequestDto branchRequestDto;
    @Spy
    Branch branch;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        branchRequestDto.setName("name");
        branchRequestDto.setFranchiseId(1L);

        branch.setName("name");
        branch.setFranchiseId(1L);
    }

    @Test
    void testCreateBranch() {
        when(branchRequestMapper.toDomain(any(BranchRequestDto.class))).thenReturn(branch);
        when(branchServicePort.saveBranch(any(Branch.class))).thenReturn(Mono.empty());

        Mono<Void> result = branchHandler.createBranch(branchRequestDto);

        StepVerifier
                .create(result)
                .verifyComplete();
    }

    @Test
    void testUpdateBranchName() {
        when(branchServicePort.updateBranchName(anyLong(), anyString())).thenReturn(Mono.empty());

        Mono<Void> result = branchHandler.updateBranchName(1L, "newName");

        StepVerifier
                .create(result)
                .verifyComplete();
    }
}