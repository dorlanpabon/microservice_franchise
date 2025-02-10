package com.pragma.franchise.infrastructure.adapters.jpa.adapter;

import com.pragma.franchise.domain.model.Branch;
import com.pragma.franchise.infrastructure.adapters.jpa.entity.BranchEntity;
import com.pragma.franchise.infrastructure.adapters.jpa.mapper.IBranchEntityMapper;
import com.pragma.franchise.infrastructure.adapters.jpa.repository.IBranchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class BranchAdapterTest {
    @Mock
    IBranchRepository branchRepository;
    @Mock
    IBranchEntityMapper branchEntityMapper;
    @InjectMocks
    BranchAdapter branchAdapter;
    @Spy
    Branch branch;
    @Spy
    BranchEntity branchEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        branch.setName("name");
        branch.setFranchiseId(1L);
        branchEntity.setName("name");
        branchEntity.setFranchiseId(1L);
    }

    @Test
    void testSave() {
        when(branchEntityMapper.toEntity(any(Branch.class))).thenReturn(branchEntity);
        when(branchRepository.save(any(BranchEntity.class))).thenReturn(Mono.empty());

        Mono<Void> result = branchAdapter.save(branch);

        StepVerifier.create(result)
                .verifyComplete();

        verify(branchEntityMapper, times(1)).toEntity(any(Branch.class));
        verify(branchRepository, times(1)).save(any(BranchEntity.class));
    }

    @Test
    void testExistsBranchByName() {
        when(branchRepository.existsByName(anyString())).thenReturn(Mono.just(true));

        Mono<Boolean> result = branchAdapter.existsBranchByName("branchName");

        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();

        verify(branchRepository, times(1)).existsByName(anyString());
    }

    @Test
    void testExistsBranchById() {
        when(branchRepository.existsById(anyLong())).thenReturn(Mono.just(true));

        Mono<Boolean> result = branchAdapter.existsBranchById(1L);

        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();

        verify(branchRepository, times(1)).existsById(anyLong());
    }

    @Test
    void testExistsBranchByNameAndFranchiseId() {
        when(branchRepository.existsByNameAndFranchiseId(anyString(), anyLong())).thenReturn(Mono.just(true));

        Mono<Boolean> result = branchAdapter.existsBranchByNameAndFranchiseId("branchName", 1L);

        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();

        verify(branchRepository, times(1)).existsByNameAndFranchiseId(anyString(), anyLong());
    }
}