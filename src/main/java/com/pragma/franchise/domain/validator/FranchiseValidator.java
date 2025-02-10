package com.pragma.franchise.domain.validator;

import com.pragma.franchise.domain.api.IFranchisePersistencePort;
import com.pragma.franchise.domain.exception.DomainException;
import com.pragma.franchise.domain.util.DomainConstants;
import reactor.core.publisher.Mono;

public class FranchiseValidator {

    private static final int MAX_NAME_LENGTH = DomainConstants.MAX_NAME_FRANCHISE_LENGTH;

    private final IFranchisePersistencePort franchisePersistencePort;

    public FranchiseValidator(IFranchisePersistencePort franchisePersistencePort) {
        this.franchisePersistencePort = franchisePersistencePort;
    }

    public Mono<Void> validateFranchiseName(String name) {
        return Mono.justOrEmpty(name)
                .filter(n -> !n.isBlank())
                .switchIfEmpty(Mono.error(new DomainException(DomainConstants.FRANCHISE_NAME_REQUIRED)))
                .filter(n -> n.length() <= MAX_NAME_LENGTH)
                .switchIfEmpty(Mono.error(new DomainException(DomainConstants.FRANCHISE_NAME_TOO_LONG)))
                .then();
    }

    public Mono<Void> validateUniqueFranchiseName(String name) {
        return franchisePersistencePort.existsByName(name)
                .filter(exists -> !exists)
                .switchIfEmpty(Mono.error(new DomainException(DomainConstants.FRANCHISE_NAME_ALREADY_EXISTS)))
                .then();
    }
}
