package com.pragma.franchise.infrastructure.entrypoints.handler.interfaces;

import com.pragma.franchise.infrastructure.entrypoints.dto.request.FranchiseRequestDto;
import reactor.core.publisher.Mono;

public interface IFranchiseHandler {

    Mono<Void> createFranchise(FranchiseRequestDto franchiseRequestDto);

}
