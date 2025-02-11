package com.pragma.franchise.infrastructure.entrypoints.handler;

import com.pragma.franchise.domain.spi.IProductServicePort;
import com.pragma.franchise.infrastructure.entrypoints.dto.request.ProductRequestDto;
import com.pragma.franchise.infrastructure.entrypoints.dto.request.ProductStockUpdateDto;
import com.pragma.franchise.infrastructure.entrypoints.dto.response.ProductStockResponseDto;
import com.pragma.franchise.infrastructure.entrypoints.handler.interfaces.IProductHandler;
import com.pragma.franchise.infrastructure.entrypoints.mapper.IProductRequestMapper;
import com.pragma.franchise.infrastructure.entrypoints.mapper.IProductResponseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductHandler implements IProductHandler {

    private final IProductServicePort productServicePort;
    private final IProductRequestMapper productRequestMapper;
    private final IProductResponseMapper productResponseMapper;

    @Override
    public Mono<ServerResponse> addProduct(ServerRequest request) {
        return request.bodyToMono(ProductRequestDto.class)
                .map(productRequestMapper::toDomain)
                .flatMap(productServicePort::saveProduct)
                .then(ServerResponse.status(HttpStatus.CREATED).build());
    }

    @Override
    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
        Long productId = Long.parseLong(request.pathVariable("productId"));
        Long branchId = Long.parseLong(request.pathVariable("branchId"));

        return productServicePort.deleteProduct(productId, branchId)
                .then(ServerResponse.noContent().build());
    }

    @Override
    public Mono<ServerResponse> updateStock(ServerRequest request) {
        Long productId = Long.parseLong(request.pathVariable("productId"));
        Long branchId = Long.parseLong(request.pathVariable("branchId"));

        return request.bodyToMono(ProductStockUpdateDto.class)
                .flatMap(dto -> productServicePort.updateStock(productId, branchId, dto.getStockChange()))
                .then(ServerResponse.noContent().build());
    }

    @Override
    public Mono<ServerResponse> getMaxStockProductByBranchForFranchise(ServerRequest request) {
        Long franchiseId = Long.parseLong(request.pathVariable("franchiseId"));

        return ServerResponse.ok().body(productServicePort.getMaxStockProductByBranchForFranchise(franchiseId)
                .map(productResponseMapper::toResponse), ProductStockResponseDto.class);
    }

    @Override
    public Mono<ServerResponse> updateProductName(ServerRequest request) {
        Long productId = Long.parseLong(request.pathVariable("id"));
        String newName = Objects.requireNonNull(request.queryParam("newName").orElse(null));

        return productServicePort.updateProductName(productId, newName)
                .then(ServerResponse.ok().build());
    }
}
