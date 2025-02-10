package com.pragma.franchise.infrastructure.adapters.jpa.adapter;

import com.pragma.franchise.domain.model.Franchise;
import com.pragma.franchise.infrastructure.adapters.jpa.entity.FranchiseEntity;
import com.pragma.franchise.infrastructure.adapters.jpa.mapper.IFranchiseEntityMapper;
import com.pragma.franchise.infrastructure.adapters.jpa.repository.IFranchiseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class FranchiseAdapterTest {
    @Mock
    IFranchiseRepository franchiseRepository;
    @Mock
    IFranchiseEntityMapper franchiseEntityMapper;
    @InjectMocks
    FranchiseAdapter franchiseAdapter;
    @Spy
    Franchise franchise;
    @Spy
    FranchiseEntity franchiseEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        franchise.setName("name");
        franchiseEntity.setName("name");
    }

    @Test
    void testSave() {
        when(franchiseEntityMapper.toEntity(any(Franchise.class))).thenReturn(franchiseEntity);
        when(franchiseRepository.save(any(FranchiseEntity.class))).thenReturn(Mono.empty());
        Mono<Void> result = franchiseAdapter.save(franchise);

        StepVerifier.create(result)
                .verifyComplete();

        verify(franchiseEntityMapper, times(1)).toEntity(any(Franchise.class));
    }
}