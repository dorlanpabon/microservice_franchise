package com.pragma.franchise.application.config;

import com.pragma.franchise.domain.api.IFranchisePersistencePort;
import com.pragma.franchise.domain.spi.IFranchiseServicePort;
import com.pragma.franchise.domain.usecase.FranchiseUseCase;
import com.pragma.franchise.domain.validator.FranchiseValidator;
import com.pragma.franchise.infrastructure.adapters.jpa.adapter.FranchiseAdapter;
import com.pragma.franchise.infrastructure.adapters.jpa.mapper.IFranchiseEntityMapper;
import com.pragma.franchise.infrastructure.adapters.jpa.repository.IFranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FranchiseUseCaseConfig {

    private final IFranchiseRepository franchiseRepository;
    private final IFranchiseEntityMapper franchiseEntityMapper;

    @Bean
    public IFranchisePersistencePort franchisePersistencePort() {
        return new FranchiseAdapter(franchiseRepository, franchiseEntityMapper);
    }

    @Bean
    public FranchiseValidator franchiseValidator() {
        return new FranchiseValidator(franchisePersistencePort());
    }

    @Bean
    public IFranchiseServicePort franchiseServicePort() {
        return new FranchiseUseCase(franchisePersistencePort(), franchiseValidator());
    }

}
