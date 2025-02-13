package com.pragma.franchise.domain.validator;

import com.pragma.franchise.domain.api.IBranchPersistencePort;
import com.pragma.franchise.domain.api.IFranchisePersistencePort;
import com.pragma.franchise.domain.exception.InvalidParameterException;
import com.pragma.franchise.domain.exception.ResourceConflictException;
import com.pragma.franchise.domain.exception.ResourceNotFoundException;
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
                .switchIfEmpty(Mono.error(new InvalidParameterException(DomainConstants.FRANCHISE_ID_REQUIRED)))
                .then();
    }

    public Mono<Void> validateFranchiseExists(Long franchiseId) {
        return franchisePersistencePort.existsFranchise(franchiseId)
                .filter(Boolean::booleanValue)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException(DomainConstants.FRANCHISE_NOT_FOUND)))
                .then();
    }

    public Mono<Void> validateBranchNameLength(String name) {
        return Mono.justOrEmpty(name)
                .filter(n -> n.length() <= MAX_NAME_LENGTH)
                .switchIfEmpty(Mono.error(new InvalidParameterException(DomainConstants.BRANCH_NAME_TOO_LONG)))
                .then();
    }

    public Mono<Void> validateUniqueBranchNameAndFranchiseId(String name, Long franchiseId) {
        return branchPersistencePort.existsBranchByNameAndFranchiseId(name, franchiseId)
                .filter(exists -> !exists)
                .switchIfEmpty(Mono.error(new ResourceConflictException(DomainConstants.BRANCH_NAME_ALREADY_EXISTS)))
                .then();
    }

    public Mono<Void> validateBranchName(String newName) {
        return Mono.justOrEmpty(newName)
                .filter(n -> n.length() <= MAX_NAME_LENGTH)
                .switchIfEmpty(Mono.error(new InvalidParameterException(DomainConstants.BRANCH_NAME_TOO_LONG)))
                .then();
    }
}
