package com.pragma.franchise.infrastructure.entrypoints.handler;

import com.pragma.franchise.domain.spi.IBranchServicePort;
import com.pragma.franchise.infrastructure.entrypoints.dto.request.BranchRequestDto;
import com.pragma.franchise.infrastructure.entrypoints.handler.interfaces.IBranchHandler;
import com.pragma.franchise.infrastructure.entrypoints.mapper.IBranchRequestMapper;
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
                .map(branchRequestMapper::toDomain)
                .flatMap(branchServicePort::saveBranch)
                .then(ServerResponse.status(HttpStatus.CREATED).build());
    }

    @Override
    public Mono<ServerResponse> updateBranchName(ServerRequest request) {
        Long branchId = Long.parseLong(Objects.requireNonNull(request.queryParams().getFirst("branchId")));
        String newName = Objects.requireNonNull(request.queryParams().getFirst("newName"));

        return branchServicePort.updateBranchName(branchId, newName)
                .then(ServerResponse.status(HttpStatus.OK).build());
    }
}
