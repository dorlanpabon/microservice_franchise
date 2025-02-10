package com.pragma.franchise.domain.api;

import com.pragma.franchise.domain.model.Branch;
import reactor.core.publisher.Mono;

public interface IBranchPersistencePort {

    Mono<Void> save(Branch branch);

    Mono<Boolean> existsBranchByName(String branchName);

    Mono<Boolean> existsBranchById(Long branchId);

    Mono<Boolean> existsBranchByNameAndFranchiseId(String name, Long franchiseId);
}
