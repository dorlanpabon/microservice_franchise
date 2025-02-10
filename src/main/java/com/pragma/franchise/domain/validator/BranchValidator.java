package com.pragma.franchise.domain.validator;

import com.pragma.franchise.domain.api.IBranchPersistencePort;
import com.pragma.franchise.domain.api.IFranchisePersistencePort;
import com.pragma.franchise.domain.exception.DomainException;
import com.pragma.franchise.domain.util.DomainConstants;
import reactor.core.publisher.Mono;

public class BranchValidator {

    private static final int MAX_NAME_LENGTH = DomainConstants.MAX_NAME_BRANCH_LENGTH;

    private final IBranchPersistencePort branchPersistencePort;
    private final IFranchisePersistencePort franchisePersistencePort;

    public BranchValidator(IBranchPersistencePort branchPersistencePort, IFranchisePersistencePort franchisePersistencePort) {
        this.branchPersistencePort = branchPersistencePort;
        this.franchisePersistencePort = franchisePersistencePort;
    }

    public Mono<Void> validateFranchiseId(Long franchiseId) {
        return Mono.justOrEmpty(franchiseId)
                .switchIfEmpty(Mono.error(new DomainException(DomainConstants.FRANCHISE_ID_REQUIRED)))
                .then();
    }

    public Mono<Void> validateFranchiseExists(Long franchiseId) {
        return franchisePersistencePort.existsFranchise(franchiseId)
                .filter(Boolean::booleanValue)
                .switchIfEmpty(Mono.error(new DomainException(DomainConstants.FRANCHISE_NOT_FOUND)))
                .then();
    }

    public Mono<Void> validateBranchNameLength(String name) {
        return Mono.justOrEmpty(name)
                .filter(n -> n.length() <= MAX_NAME_LENGTH)
                .switchIfEmpty(Mono.error(new DomainException(DomainConstants.BRANCH_NAME_TOO_LONG)))
                .then();
    }

    public Mono<Void> validateUniqueBranchName(String branchName) {
        return branchPersistencePort.existsBranchByName(branchName)
                .filter(exists -> !exists)
                .switchIfEmpty(Mono.error(new DomainException(DomainConstants.BRANCH_NAME_ALREADY_EXISTS)))
                .then();
    }
}
