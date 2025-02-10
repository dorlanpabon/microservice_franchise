package com.pragma.franchise.infrastructure.entrypoints.handler;

import com.pragma.franchise.domain.spi.IBranchServicePort;
import com.pragma.franchise.infrastructure.entrypoints.dto.request.BranchRequestDto;
import com.pragma.franchise.infrastructure.entrypoints.handler.interfaces.IBranchHandler;
import com.pragma.franchise.infrastructure.entrypoints.mapper.IBranchRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
@RequiredArgsConstructor
public class BranchHandler implements IBranchHandler {

    private final IBranchServicePort branchServicePort;
    private final IBranchRequestMapper branchRequestMapper;

    @Override
    public Mono<Void> createBranch(BranchRequestDto branchRequestDto) {
        return Mono.just(branchRequestDto)
                .map(branchRequestMapper::toDomain)
                .flatMap(branchServicePort::saveBranch)
                .then();
    }

    @Override
    public Mono<Void> updateBranchName(Long branchId, String newName) {
        return branchServicePort.updateBranchName(branchId, newName);
    }

}
