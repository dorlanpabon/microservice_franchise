package com.pragma.franchise.infrastructure.entrypoints.handler.interfaces;

import com.pragma.franchise.infrastructure.entrypoints.dto.request.BranchRequestDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBranchHandler {

    Mono<Void> createBranch(BranchRequestDto branchRequestDto);

    Mono<Void> updateBranchName(Long id, String newName);

}
