package com.pragma.franchise.infrastructure.entrypoints.handler;

import com.pragma.franchise.domain.exception.InvalidParameterException;
import com.pragma.franchise.domain.exception.ResourceConflictException;
import com.pragma.franchise.domain.exception.ResourceNotFoundException;
import com.pragma.franchise.domain.spi.IFranchiseServicePort;
import com.pragma.franchise.domain.util.DomainConstants;
import com.pragma.franchise.domain.util.ExternalConstants;
import com.pragma.franchise.infrastructure.entrypoints.dto.request.FranchiseRequestDto;
import com.pragma.franchise.infrastructure.entrypoints.dto.response.ErrorResponseDto;
import com.pragma.franchise.infrastructure.entrypoints.handler.interfaces.IFranchiseHandler;
import com.pragma.franchise.infrastructure.entrypoints.mapper.IFranchiseRequestMapper;
import com.pragma.franchise.infrastructure.entrypoints.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class FranchiseHandler implements IFranchiseHandler {

    private final IFranchiseServicePort franchiseServicePort;
    private final IFranchiseRequestMapper franchiseRequestMapper;

    @Override
    public Mono<ServerResponse> createFranchise(ServerRequest request) {
        return request.bodyToMono(FranchiseRequestDto.class)
                .switchIfEmpty(Mono.error(InvalidParameterException.of(ExternalConstants.FRANCHISE_DATA_REQUIRED)))
                .map(franchiseRequestMapper::toDomain)
                .flatMap(franchiseServicePort::save)
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
    public Mono<ServerResponse> updateFranchiseName(ServerRequest request) {
        return Mono.defer(() -> {
                    Long franchiseId = request.queryParam("franchiseId")
                            .map(Long::parseLong)
                            .orElseThrow(() -> InvalidParameterException.of(ExternalConstants.FRANCHISE_ID_REQUIRED));

                    String newName = request.queryParam("newName")
                            .orElseThrow(() -> InvalidParameterException.of(ExternalConstants.FRANCHISE_NEW_NAME_REQUIRED));

                    return franchiseServicePort.updateFranchiseName(franchiseId, newName)
                            .then(ServerResponse.status(HttpStatus.OK).build());
                })
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

}
