package com.pragma.franchise.infrastructure.entrypoints.handler.interfaces;

import com.pragma.franchise.infrastructure.entrypoints.dto.request.FranchiseRequestDto;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface IFranchiseHandler {

    Mono<ServerResponse> createFranchise(ServerRequest request);

    Mono<ServerResponse> updateFranchiseName(ServerRequest request);

}
