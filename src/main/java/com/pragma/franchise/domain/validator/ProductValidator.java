package com.pragma.franchise.domain.validator;

import com.pragma.franchise.domain.api.IBranchPersistencePort;
import com.pragma.franchise.domain.api.IProductPersistencePort;
import com.pragma.franchise.domain.exception.InvalidParameterException;
import com.pragma.franchise.domain.exception.ResourceConflictException;
import com.pragma.franchise.domain.exception.ResourceNotFoundException;
import com.pragma.franchise.domain.util.DomainConstants;
import reactor.core.publisher.Mono;

public class ProductValidator {

    private final IBranchPersistencePort branchPersistencePort;
    private final IProductPersistencePort productPersistencePort;

    public ProductValidator(IBranchPersistencePort branchPersistencePort, IProductPersistencePort productPersistencePort) {
        this.branchPersistencePort = branchPersistencePort;
        this.productPersistencePort = productPersistencePort;
    }

    public Mono<Void> validateBranchExists(Long branchId) {
        return branchPersistencePort.existsBranchById(branchId)
                .filter(Boolean::booleanValue)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException(DomainConstants.BRANCH_NOT_FOUND)))
                .then();
    }

    public Mono<Void> validateProductName(String name) {
        return Mono.justOrEmpty(name)
                .switchIfEmpty(Mono.error(new InvalidParameterException(DomainConstants.PRODUCT_NAME_REQUIRED)))
                .then();
    }

    public Mono<Void> validateStock(Integer stock) {
        return Mono.justOrEmpty(stock)
                .filter(s -> s >= 0)
                .switchIfEmpty(Mono.error(new InvalidParameterException(DomainConstants.PRODUCT_INVALID_STOCK)))
                .then();
    }

    public Mono<Void> validateUniqueProductName(String productName, Long branchId) {
        return productPersistencePort.existsProductByNameAndBranchId(productName, branchId)
                .filter(exists -> !exists)
                .switchIfEmpty(Mono.error(new ResourceConflictException(DomainConstants.PRODUCT_NAME_ALREADY_EXISTS)))
                .then();
    }

    public Mono<Void> validateProductExists(Long productId, Long branchId) {
        return productPersistencePort.existsByIdAndBranchId(productId, branchId)
                .filter(Boolean::booleanValue)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException(DomainConstants.PRODUCT_NOT_FOUND)))
                .then();
    }

    public Mono<Void> validateStockUpdate(Long productId, Long branchId, Integer stockChange) {
        return productPersistencePort.findByIdAndBranchId(productId, branchId)
                .filter(product -> product.getStock() + stockChange >= 0)
                .switchIfEmpty(Mono.error(new InvalidParameterException(DomainConstants.PRODUCT_OUT_OF_STOCK)))
                .then();
    }
}
