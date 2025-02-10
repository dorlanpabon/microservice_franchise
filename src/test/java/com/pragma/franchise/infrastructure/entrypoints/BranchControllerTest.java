package com.pragma.franchise.infrastructure.entrypoints;

import com.pragma.franchise.infrastructure.entrypoints.dto.request.BranchRequestDto;
import com.pragma.franchise.infrastructure.entrypoints.handler.interfaces.IBranchHandler;
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

class BranchControllerTest {
    @Mock
    IBranchHandler branchHandler;
    @InjectMocks
    BranchController branchController;
    @Spy
    BranchRequestDto branchRequestDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        branchRequestDto.setName("name");
        branchRequestDto.setFranchiseId(1L);
    }

    @Test
    void testCreateBranch() {
        when(branchHandler.createBranch(any(BranchRequestDto.class))).thenReturn(Mono.empty());

        Mono<ResponseEntity<Void>> result = branchController.createBranch(branchRequestDto);

        StepVerifier
                .create(result)
                .expectNext(ResponseEntity.status(201).build())
                .verifyComplete();

        verify(branchHandler, times(1)).createBranch(any(BranchRequestDto.class));
    }

    @Test
    void testUpdateBranchName() {
        when(branchHandler.updateBranchName(anyLong(), anyString())).thenReturn(Mono.empty());

        Mono<ResponseEntity<Void>> result = branchController.updateBranchName(1L, "newName");

        StepVerifier
                .create(result)
                .expectNext(ResponseEntity.ok().build())
                .verifyComplete();

        verify(branchHandler, times(1)).updateBranchName(anyLong(), anyString());
    }
}