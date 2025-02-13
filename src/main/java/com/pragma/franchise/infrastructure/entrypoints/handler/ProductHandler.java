package com.pragma.franchise.infrastructure.entrypoints.handler;

import com.pragma.franchise.domain.exception.InvalidParameterException;
import com.pragma.franchise.domain.exception.ResourceConflictException;
import com.pragma.franchise.domain.exception.ResourceNotFoundException;
import com.pragma.franchise.domain.spi.IProductServicePort;
import com.pragma.franchise.domain.util.ExternalConstants;
import com.pragma.franchise.infrastructure.entrypoints.dto.request.ProductRequestDto;
import com.pragma.franchise.infrastructure.entrypoints.dto.request.ProductStockUpdateDto;
import com.pragma.franchise.infrastructure.entrypoints.dto.response.ProductStockResponseDto;
import com.pragma.franchise.infrastructure.entrypoints.handler.interfaces.IProductHandler;
import com.pragma.franchise.infrastructure.entrypoints.mapper.IProductRequestMapper;
import com.pragma.franchise.infrastructure.entrypoints.mapper.IProductResponseMapper;
import com.pragma.franchise.infrastructure.entrypoints.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Optional;

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
                .switchIfEmpty(Mono.error(InvalidParameterException.of(ExternalConstants.PRODUCT_DATA_REQUIRED)))
                .map(productRequestMapper::toDomain)
                .flatMap(productServicePort::saveProduct)
                .then(ServerResponse.status(HttpStatus.CREATED).build())
                .onErrorResume(InvalidParameterException.class, e ->
                        ResponseUtil.buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage()))
                .onErrorResume(ResourceConflictException.class, e ->
                        ResponseUtil.buildErrorResponse(HttpStatus.CONFLICT, e.getMessage()))
                .onErrorResume(ResourceNotFoundException.class, e ->
                        ResponseUtil.buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage()))
                .onErrorResume(e ->
                        ResponseUtil.buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ExternalConstants.INTERNAL_ERROR)
                );
    }

    @Override
    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
        return Mono.defer(() -> {
                    Long productId = Optional.of(request.pathVariable("productId"))
                            .map(Long::parseLong)
                            .orElseThrow(() -> InvalidParameterException.of(ExternalConstants.PRODUCT_ID_REQUIRED));

                    Long branchId = Optional.of(request.pathVariable("branchId"))
                            .map(Long::parseLong)
                            .orElseThrow(() -> InvalidParameterException.of(ExternalConstants.BRANCH_ID_REQUIRED));

                    return productServicePort.deleteProduct(productId, branchId)
                            .then(ServerResponse.status(HttpStatus.NO_CONTENT).build());
                }).onErrorResume(InvalidParameterException.class, e ->
                        ResponseUtil.buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage()))
                .onErrorResume(ResourceNotFoundException.class, e ->
                        ResponseUtil.buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage()))
                .onErrorResume(e ->
                        ResponseUtil.buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ExternalConstants.INTERNAL_ERROR)
                );
    }

    @Override
    public Mono<ServerResponse> updateStock(ServerRequest request) {
        return Mono.defer(() -> {
                    Long productId = Optional.of(request.pathVariable("productId"))
                            .map(Long::parseLong)
                            .orElseThrow(() -> InvalidParameterException.of(ExternalConstants.PRODUCT_ID_REQUIRED));

                    Long branchId = Optional.of(request.pathVariable("branchId"))
                            .map(Long::parseLong)
                            .orElseThrow(() -> InvalidParameterException.of(ExternalConstants.BRANCH_ID_REQUIRED));

                    return request.bodyToMono(ProductStockUpdateDto.class)
                            .flatMap(dto -> productServicePort.updateStock(productId, branchId, dto.getStockChange()))
                            .then(ServerResponse.noContent().build());
                }).onErrorResume(InvalidParameterException.class, e ->
                        ResponseUtil.buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage()))
                .onErrorResume(ResourceNotFoundException.class, e ->
                        ResponseUtil.buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage()))
                .onErrorResume(e ->
                        ResponseUtil.buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ExternalConstants.INTERNAL_ERROR)
                );
    }

    @Override
    public Mono<ServerResponse> getMaxStockProductByBranchForFranchise(ServerRequest request) {
        return Mono.defer(() -> {
            Long franchiseId = Optional.of(request.pathVariable("franchiseId"))
                .map(Long::parseLong)
                .orElseThrow(() -> InvalidParameterException.of(ExternalConstants.FRANCHISE_ID_REQUIRED));

            return productServicePort.getMaxStockProductByBranchForFranchise(franchiseId)
                    .map(productResponseMapper::toResponse)
                    .collectList()
                    .flatMap(products -> ServerResponse.ok().bodyValue(products));
        })
        .onErrorResume(InvalidParameterException.class, e ->
            ResponseUtil.buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage()))
        .onErrorResume(e ->
            ResponseUtil.buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ExternalConstants.INTERNAL_ERROR)
        );
    }

    @Override
    public Mono<ServerResponse> updateProductName(ServerRequest request) {
        return Mono.defer(() -> {
                    Long productId = Optional.of(request.pathVariable("id"))
                            .map(Long::parseLong)
                            .orElseThrow(() -> InvalidParameterException.of(ExternalConstants.PRODUCT_ID_REQUIRED));

                    String newName = request.queryParam("newName")
                            .orElseThrow(() -> InvalidParameterException.of(ExternalConstants.PRODUCT_NEW_NAME_REQUIRED));

                    return productServicePort.updateProductName(productId, newName)
                            .then(ServerResponse.ok().build());
                }).onErrorResume(InvalidParameterException.class, e ->
                        ResponseUtil.buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage()))
                .onErrorResume(ResourceConflictException.class, e ->
                        ResponseUtil.buildErrorResponse(HttpStatus.CONFLICT, e.getMessage()))
                .onErrorResume(ResourceNotFoundException.class, e ->
                        ResponseUtil.buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage()))
                .onErrorResume(e ->
                        ResponseUtil.buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ExternalConstants.INTERNAL_ERROR)
                );
    }
}
