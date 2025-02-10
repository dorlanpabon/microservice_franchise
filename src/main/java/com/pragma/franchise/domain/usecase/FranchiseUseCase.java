package com.pragma.franchise.domain.usecase;

import com.pragma.franchise.domain.api.IFranchisePersistencePort;
import com.pragma.franchise.domain.model.Franchise;
import com.pragma.franchise.domain.spi.IFranchiseServicePort;
import com.pragma.franchise.domain.validator.FranchiseValidator;
import reactor.core.publisher.Mono;

public class FranchiseUseCase implements IFranchiseServicePort {

    private final IFranchisePersistencePort franchisePersistencePort;
    private final FranchiseValidator franchiseValidator;

    public FranchiseUseCase(IFranchisePersistencePort franchisePersistencePort, FranchiseValidator franchiseValidator) {
        this.franchisePersistencePort = franchisePersistencePort;
        this.franchiseValidator = franchiseValidator;
    }

    @Override
    public Mono<Void> save(Franchise franchise) {
        return Mono.when(
                        franchiseValidator.validateFranchiseName(franchise.getName()),
                        franchiseValidator.validateUniqueFranchiseName(franchise.getName())
                )
                .then(franchisePersistencePort.save(franchise));
    }

}
