package com.pragma.franchise.infraestructure.entrypoints.handler;

import com.pragma.franchise.domain.spi.IFranchiseServicePort;
import com.pragma.franchise.infraestructure.entrypoints.dto.request.FranchiseRequestDto;
import com.pragma.franchise.infraestructure.entrypoints.handler.interfaces.IFranchiseHandler;
import com.pragma.franchise.infraestructure.entrypoints.mapper.IFranchiseRequestMapper;
import com.pragma.franchise.infraestructure.entrypoints.mapper.IFranchiseResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
@RequiredArgsConstructor
public class FranchiseHandler implements IFranchiseHandler {

    private final IFranchiseServicePort franchiseServicePort;
    private final IFranchiseRequestMapper franchiseRequestMapper;
    private final IFranchiseResponseMapper franchiseResponseMapper;

    @Override
    public Mono<Void> createFranchise(FranchiseRequestDto franchiseRequestDto) {
        return Mono.just(franchiseRequestDto)
                .map(franchiseRequestMapper::toDomain)
                .flatMap(franchiseServicePort::save)
                .then();
    }
}
