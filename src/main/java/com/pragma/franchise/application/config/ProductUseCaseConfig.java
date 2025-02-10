package com.pragma.franchise.application.config;

import com.pragma.franchise.domain.api.IBranchPersistencePort;
import com.pragma.franchise.domain.api.IProductPersistencePort;
import com.pragma.franchise.domain.spi.IProductServicePort;
import com.pragma.franchise.domain.usecase.ProductUseCase;
import com.pragma.franchise.domain.validator.ProductValidator;
import com.pragma.franchise.infrastructure.adapters.jpa.adapter.ProductAdapter;
import com.pragma.franchise.infrastructure.adapters.jpa.mapper.IProductEntityMapper;
import com.pragma.franchise.infrastructure.adapters.jpa.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ProductUseCaseConfig {

    private final IProductRepository productRepository;
    private final IProductEntityMapper productEntityMapper;
    private final IBranchPersistencePort branchPersistencePort; // Para validar si la sucursal existe

    @Bean
    public IProductPersistencePort productPersistencePort() {
        return new ProductAdapter(productRepository, productEntityMapper);
    }

    @Bean
    public ProductValidator productValidator() {
        return new ProductValidator(branchPersistencePort);
    }

    @Bean
    public IProductServicePort productServicePort() {
        return new ProductUseCase(productPersistencePort(), productValidator());
    }
}
