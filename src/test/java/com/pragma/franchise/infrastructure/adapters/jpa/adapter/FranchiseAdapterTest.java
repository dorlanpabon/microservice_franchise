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

    @Test
    void testExistsFranchise() {
        when(franchiseRepository.existsById(anyLong())).thenReturn(Mono.just(true));
        Mono<Boolean> result = franchiseAdapter.existsFranchise(1L);

        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();

        verify(franchiseRepository, times(1)).existsById(anyLong());
    }

    @Test
    void testExistsByName() {
        when(franchiseRepository.existsByName(anyString())).thenReturn(Mono.just(true));
        Mono<Boolean> result = franchiseAdapter.existsByName("name");

        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();

        verify(franchiseRepository, times(1)).existsByName(anyString());
    }

    @Test
    void testFindById() {
        when(franchiseRepository.findById(anyLong())).thenReturn(Mono.just(franchiseEntity));
        when(franchiseEntityMapper.toDomain(any(FranchiseEntity.class))).thenReturn(franchise);
        Mono<Franchise> result = franchiseAdapter.findById(1L);

        StepVerifier.create(result)
                .expectNext(franchise)
                .verifyComplete();

        verify(franchiseRepository, times(1)).findById(anyLong());
        verify(franchiseEntityMapper, times(1)).toDomain(any(FranchiseEntity.class));
    }
}