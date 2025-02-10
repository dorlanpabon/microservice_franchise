package com.pragma.franchise.domain.usecase;

import com.pragma.franchise.domain.api.IProductPersistencePort;
import com.pragma.franchise.domain.model.Product;
import com.pragma.franchise.domain.spi.IProductServicePort;
import com.pragma.franchise.domain.validator.ProductValidator;
import lombok.RequiredArgsConstructor;
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
}
