package com.pragma.franchise.infrastructure.entrypoints.handler;

import com.pragma.franchise.domain.spi.IFranchiseServicePort;
import com.pragma.franchise.infrastructure.entrypoints.dto.request.FranchiseRequestDto;
import com.pragma.franchise.infrastructure.entrypoints.handler.interfaces.IFranchiseHandler;
import com.pragma.franchise.infrastructure.entrypoints.mapper.IFranchiseRequestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class FranchiseHandler implements IFranchiseHandler {

    private final IFranchiseServicePort franchiseServicePort;
    private final IFranchiseRequestMapper franchiseRequestMapper;

    @Override
    public Mono<ServerResponse> createFranchise(ServerRequest request) {
        return request.bodyToMono(FranchiseRequestDto.class)
                .map(franchiseRequestMapper::toDomain)
                .flatMap(franchiseServicePort::save)
                .then(ServerResponse.status(HttpStatus.CREATED).build());
    }

    @Override
    public Mono<ServerResponse> updateFranchiseName(ServerRequest request) {
        Long franchiseId = Long.parseLong(Objects.requireNonNull(request.queryParams().getFirst("franchiseId")));
        String newName = Objects.requireNonNull(request.queryParams().getFirst("newName"));
        return franchiseServicePort.updateFranchiseName(franchiseId, newName)
                .then(ServerResponse.status(HttpStatus.OK).build());
    }
}
