package com.pragma.franchise.domain.spi;

import com.pragma.franchise.domain.model.Branch;
import reactor.core.publisher.Mono;

public interface IBranchServicePort {

    Mono<Void> saveBranch(Branch branch);

    Mono<Void> updateBranchName(Long branchId, String newName);

}

