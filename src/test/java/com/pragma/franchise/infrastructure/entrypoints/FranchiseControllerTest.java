package com.pragma.franchise.infrastructure.entrypoints;

import com.pragma.franchise.infrastructure.entrypoints.dto.request.FranchiseRequestDto;
import com.pragma.franchise.infrastructure.entrypoints.handler.interfaces.IFranchiseHandler;
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

class FranchiseControllerTest {
    @Mock
    IFranchiseHandler franchiseHandler;
    @InjectMocks
    FranchiseController franchiseController;
    @Spy
    FranchiseRequestDto franchiseRequestDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        franchiseRequestDto.setName("name");
    }

    @Test
    void testCreateFranchise() {
        when(franchiseHandler.createFranchise(any(FranchiseRequestDto.class))).thenReturn(Mono.empty());

        Mono<ResponseEntity<Void>> result = franchiseController.createFranchise(franchiseRequestDto);

        StepVerifier.create(result)
                .expectNext(ResponseEntity.status(201).build())
                .verifyComplete();

        verify(franchiseHandler, times(1)).createFranchise(any(FranchiseRequestDto.class));
    }

    @Test
    void testUpdateFranchiseName() {
        when(franchiseHandler.updateFranchiseName(anyLong(), anyString())).thenReturn(Mono.empty());

        Mono<ResponseEntity<Void>> result = franchiseController.updateFranchiseName(1L, "newName");

        StepVerifier.create(result)
                .expectNext(ResponseEntity.ok().build())
                .verifyComplete();

        verify(franchiseHandler, times(1)).updateFranchiseName(anyLong(), anyString());
    }
}
