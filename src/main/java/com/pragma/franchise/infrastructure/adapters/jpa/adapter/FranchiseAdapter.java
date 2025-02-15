package com.pragma.franchise.infrastructure.adapters.jpa.adapter;

import com.pragma.franchise.domain.api.IFranchisePersistencePort;
import com.pragma.franchise.domain.model.Franchise;
import com.pragma.franchise.infrastructure.adapters.jpa.mapper.IFranchiseEntityMapper;
import com.pragma.franchise.infrastructure.adapters.jpa.repository.IFranchiseRepository;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class FranchiseAdapter implements IFranchisePersistencePort {

    private final IFranchiseRepository franchiseRepository;
    private final IFranchiseEntityMapper franchiseEntityMapper;

    @Override
    public Mono<Void> save(Franchise franchise) {
        return Mono.just(franchise)
                .map(franchiseEntityMapper::toEntity)
                .flatMap(franchiseRepository::save)
                .then();
    }

    @Override
    public Mono<Boolean> existsFranchise(Long franchiseId) {
        return franchiseRepository.existsById(franchiseId);
    }

    @Override
    public Mono<Boolean> existsByName(String name) {
        return franchiseRepository.existsByName(name);
    }

    @Override
    public Mono<Franchise> findById(Long franchiseId) {
        return franchiseRepository.findById(franchiseId)
                .map(franchiseEntityMapper::toDomain);
    }
}
