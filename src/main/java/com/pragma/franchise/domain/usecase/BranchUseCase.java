package com.pragma.franchise.domain.usecase;

import com.pragma.franchise.domain.api.IBranchPersistencePort;
import com.pragma.franchise.domain.exception.InvalidParameterException;
import com.pragma.franchise.domain.exception.ResourceNotFoundException;
import com.pragma.franchise.domain.model.Branch;
import com.pragma.franchise.domain.spi.IBranchServicePort;
import com.pragma.franchise.domain.util.DomainConstants;
import com.pragma.franchise.domain.validator.BranchValidator;
import reactor.core.publisher.Mono;

public class BranchUseCase implements IBranchServicePort {

    private final IBranchPersistencePort branchPersistencePort;
    private final BranchValidator branchValidator;


    public BranchUseCase(IBranchPersistencePort branchPersistencePort, BranchValidator branchValidator) {
        this.branchPersistencePort = branchPersistencePort;
        this.branchValidator = branchValidator;
    }

    @Override
    public Mono<Void> saveBranch(Branch branch) {
        return Mono.when(
                        branchValidator.validateFranchiseId(branch.getFranchiseId()),
                        branchValidator.validateFranchiseExists(branch.getFranchiseId()),
                        branchValidator.validateBranchNameLength(branch.getName()),
                        branchValidator.validateUniqueBranchNameAndFranchiseId(branch.getName(), branch.getFranchiseId())
                )
                .then(branchPersistencePort.save(branch));
    }

    @Override
    public Mono<Void> updateBranchName(Long branchId, String newName) {
        return branchPersistencePort.findById(branchId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.of(DomainConstants.BRANCH_NOT_FOUND)))
                .flatMap(branch -> branchValidator.validateBranchName(newName)
                        .then(branchValidator.validateUniqueBranchNameAndFranchiseId(newName, branch.getFranchiseId()))
                        .then(Mono.fromRunnable(() -> branch.setName(newName)))
                        .then(branchPersistencePort.save(branch)));
    }

}
