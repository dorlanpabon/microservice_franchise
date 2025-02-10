package com.pragma.franchise.application.config;

import com.pragma.franchise.domain.api.IBranchPersistencePort;
import com.pragma.franchise.domain.api.IFranchisePersistencePort;
import com.pragma.franchise.domain.spi.IBranchServicePort;
import com.pragma.franchise.domain.usecase.BranchUseCase;
import com.pragma.franchise.domain.validator.BranchValidator;
import com.pragma.franchise.infrastructure.adapters.jpa.adapter.BranchAdapter;
import com.pragma.franchise.infrastructure.adapters.jpa.mapper.IBranchEntityMapper;
import com.pragma.franchise.infrastructure.adapters.jpa.repository.IBranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BranchUseCaseConfig {

    private final IBranchRepository branchRepository;
    private final IBranchEntityMapper branchEntityMapper;

    @Bean
    public IBranchPersistencePort branchPersistencePort() {
        return new BranchAdapter(branchRepository, branchEntityMapper);
    }

    @Bean
    public BranchValidator branchValidator(IFranchisePersistencePort franchisePersistencePort) {
        return new BranchValidator(branchPersistencePort(), franchisePersistencePort);
    }

    @Bean
    public IBranchServicePort branchServicePort(IFranchisePersistencePort franchisePersistencePort) {
        return new BranchUseCase(branchPersistencePort(), branchValidator(franchisePersistencePort));
    }

}
