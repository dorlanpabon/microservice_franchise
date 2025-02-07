package com.pragma.franchise.infraestructure.entrypoints.handler.interfaces;

import com.pragma.franchise.infraestructure.entrypoints.dto.request.FranchiseRequestDto;
import reactor.core.publisher.Mono;

public interface IFranchiseHandler {

    Mono<Void> createFranchise(FranchiseRequestDto franchiseRequestDto);

}
