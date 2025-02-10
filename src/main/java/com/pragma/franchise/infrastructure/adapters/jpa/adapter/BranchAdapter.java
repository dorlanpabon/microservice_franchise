package com.pragma.franchise.infrastructure.adapters.jpa.adapter;

import com.pragma.franchise.domain.api.IBranchPersistencePort;
import com.pragma.franchise.domain.model.Branch;
import com.pragma.franchise.infrastructure.adapters.jpa.mapper.IBranchEntityMapper;
import com.pragma.franchise.infrastructure.adapters.jpa.repository.IBranchRepository;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class BranchAdapter implements IBranchPersistencePort {

    private final IBranchRepository branchRepository;
    private final IBranchEntityMapper branchEntityMapper;

    @Override
    public Mono<Void> save(Branch branch) {
        return Mono.just(branch)
                .map(branchEntityMapper::toEntity)
                .flatMap(branchRepository::save)
                .then();
    }

    @Override
    public Mono<Boolean> existsBranchByName(String branchName) {
        return branchRepository.existsByName(branchName);
    }

    @Override
    public Mono<Boolean> existsBranchById(Long branchId) {
        return branchRepository.existsById(branchId);
    }
}
