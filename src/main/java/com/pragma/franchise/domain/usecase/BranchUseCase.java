package com.pragma.franchise.domain.usecase;

import com.pragma.franchise.domain.api.IBranchPersistencePort;
import com.pragma.franchise.domain.model.Branch;
import com.pragma.franchise.domain.spi.IBranchServicePort;
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

}
