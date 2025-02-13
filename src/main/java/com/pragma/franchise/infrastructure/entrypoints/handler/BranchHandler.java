package com.pragma.franchise.infrastructure.entrypoints.handler;

import com.pragma.franchise.domain.exception.InvalidParameterException;
import com.pragma.franchise.domain.exception.ResourceConflictException;
import com.pragma.franchise.domain.exception.ResourceNotFoundException;
import com.pragma.franchise.domain.spi.IBranchServicePort;
import com.pragma.franchise.domain.util.ExternalConstants;
import com.pragma.franchise.infrastructure.entrypoints.dto.request.BranchRequestDto;
import com.pragma.franchise.infrastructure.entrypoints.handler.interfaces.IBranchHandler;
import com.pragma.franchise.infrastructure.entrypoints.mapper.IBranchRequestMapper;
import com.pragma.franchise.infrastructure.entrypoints.util.ResponseUtil;
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
public class BranchHandler implements IBranchHandler {

    private final IBranchServicePort branchServicePort;
    private final IBranchRequestMapper branchRequestMapper;

    @Override
    public Mono<ServerResponse> createBranch(ServerRequest request) {
        return request.bodyToMono(BranchRequestDto.class)
                .switchIfEmpty(Mono.error(InvalidParameterException.of(ExternalConstants.BRANCH_DATA_REQUIRED)))
                .map(branchRequestMapper::toDomain)
                .flatMap(branchServicePort::saveBranch)
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
    public Mono<ServerResponse> updateBranchName(ServerRequest request) {
        return Mono.defer(() -> {
                    Long branchId = request.queryParam("branchId")
                            .map(Long::parseLong)
                            .orElseThrow(() -> InvalidParameterException.of(ExternalConstants.BRANCH_ID_REQUIRED));

                    String newName = request.queryParam("newName")
                            .orElseThrow(() ->InvalidParameterException.of(ExternalConstants.BRANCH_NEW_NAME_REQUIRED));

                    return branchServicePort.updateBranchName(branchId, newName)
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
