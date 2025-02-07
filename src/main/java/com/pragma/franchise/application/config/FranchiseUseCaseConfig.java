package com.pragma.franchise.application.config;

import com.pragma.franchise.domain.api.IFranchisePersistencePort;
import com.pragma.franchise.domain.spi.IFranchiseServicePort;
import com.pragma.franchise.domain.usecase.FranchiseUseCase;
import com.pragma.franchise.infraestructure.adapters.jpa.adapter.FranchiseAdapter;
import com.pragma.franchise.infraestructure.adapters.jpa.mapper.IFranchiseEntityMapper;
import com.pragma.franchise.infraestructure.adapters.jpa.repository.IFranchiseRepository;
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
    public IFranchiseServicePort franchiseServicePort() {
        return new FranchiseUseCase(franchisePersistencePort());
    }

}
