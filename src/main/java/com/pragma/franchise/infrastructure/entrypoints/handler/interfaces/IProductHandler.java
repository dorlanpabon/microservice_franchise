package com.pragma.franchise.infrastructure.entrypoints.handler.interfaces;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface IProductHandler {

    Mono<ServerResponse> addProduct(ServerRequest request);

    Mono<ServerResponse> deleteProduct(ServerRequest request);

    Mono<ServerResponse> updateStock(ServerRequest request);

    Mono<ServerResponse> getMaxStockProductByBranchForFranchise(ServerRequest request);

    Mono<ServerResponse> updateProductName(ServerRequest request);
}
