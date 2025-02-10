package com.pragma.franchise.infrastructure.entrypoints.handler;

import com.pragma.franchise.domain.model.Franchise;
import com.pragma.franchise.domain.spi.IFranchiseServicePort;
import com.pragma.franchise.infrastructure.entrypoints.dto.request.FranchiseRequestDto;
import com.pragma.franchise.infrastructure.entrypoints.mapper.IFranchiseRequestMapper;
import com.pragma.franchise.infrastructure.entrypoints.mapper.IFranchiseResponseMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class FranchiseHandlerTest {
    @Mock
    IFranchiseServicePort franchiseServicePort;
    @Mock
    IFranchiseRequestMapper franchiseRequestMapper;
    @Mock
    IFranchiseResponseMapper franchiseResponseMapper;
    @InjectMocks
    FranchiseHandler franchiseHandler;
    @Spy
    FranchiseRequestDto franchiseRequestDto;
    @Spy
    Franchise franchise;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        franchiseRequestDto.setName("name");
        franchise.setName("name");
    }

    @Test
    void testCreateFranchise() {
        when(franchiseRequestMapper.toDomain(any(FranchiseRequestDto.class))).thenReturn(franchise);
        when(franchiseServicePort.save(any(Franchise.class))).thenReturn(Mono.empty());
        Mono<Void> result = franchiseHandler.createFranchise(franchiseRequestDto);

        StepVerifier.create(result)
                .verifyComplete();

        verify(franchiseRequestMapper, times(1)).toDomain(any(FranchiseRequestDto.class));
    }
}
