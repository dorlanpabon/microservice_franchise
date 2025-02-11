package com.pragma.franchise.infrastructure.entrypoints.handler.interfaces;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface IBranchHandler {

    Mono<ServerResponse> createBranch(ServerRequest request);

    Mono<ServerResponse> updateBranchName(ServerRequest request);
}
