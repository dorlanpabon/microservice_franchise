package com.pragma.franchise.domain.usecase;

import com.pragma.franchise.domain.api.IProductPersistencePort;
import com.pragma.franchise.domain.exception.InvalidParameterException;
import com.pragma.franchise.domain.exception.ResourceNotFoundException;
import com.pragma.franchise.domain.model.Product;
import com.pragma.franchise.domain.spi.IProductServicePort;
import com.pragma.franchise.domain.util.DomainConstants;
import com.pragma.franchise.domain.validator.ProductValidator;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ProductUseCase implements IProductServicePort {

    private final IProductPersistencePort productPersistencePort;
    private final ProductValidator productValidator;

    @Override
    public Mono<Void> saveProduct(Product product) {
        return Mono.when(
                        productValidator.validateBranchExists(product.getBranchId()),
                        productValidator.validateProductName(product.getName()),
                        productValidator.validateStock(product.getStock()),
                        productValidator.validateUniqueProductName(product.getName(), product.getBranchId())
                )
                .then(productPersistencePort.save(product));
    }

    @Override
    public Mono<Void> deleteProduct(Long productId, Long branchId) {
        return productValidator.validateProductExists(productId, branchId)
                .then(productPersistencePort.deleteByIdAndBranchId(productId, branchId));
    }

    @Override
    public Mono<Void> updateStock(Long productId, Long branchId, Integer stockChange) {
        return productValidator.validateProductExists(productId, branchId)
                .then(productValidator.validateStockUpdate(productId, branchId, stockChange))
                .then(productPersistencePort.findByIdAndBranchId(productId, branchId))
                .flatMap(product -> {
                    int newStock = product.getStock() + stockChange;
                    return productPersistencePort.updateStock(productId, branchId, newStock);
                });
    }

    @Override
    public Flux<Product> getMaxStockProductByBranchForFranchise(Long franchiseId) {
        return productPersistencePort.findMaxStockProductByBranchForFranchise(franchiseId);
    }

    @Override
    public Mono<Void> updateProductName(Long productId, String newName) {
        return productPersistencePort.findById(productId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.of(DomainConstants.PRODUCT_NOT_FOUND)))
                .flatMap(product -> productValidator.validateProductName(newName)
                        .then(productValidator.validateUniqueProductName(newName, product.getBranchId()))
                        .then(Mono.fromRunnable(() -> product.setName(newName)))
                        .then(productPersistencePort.save(product)));
    }

}
