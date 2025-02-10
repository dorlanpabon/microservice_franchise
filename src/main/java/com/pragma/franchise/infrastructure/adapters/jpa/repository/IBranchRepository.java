package com.pragma.franchise.infrastructure.adapters.jpa.repository;

import com.pragma.franchise.infrastructure.adapters.jpa.entity.BranchEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface IBranchRepository extends ReactiveCrudRepository<BranchEntity, Long> {

    Mono<Boolean> existsByName(String branchName);

    Mono<Boolean> existsByNameAndFranchiseId(String name, Long franchiseId);
}
