package com.pragma.franchise.domain.api;

import com.pragma.franchise.domain.model.Product;
import reactor.core.publisher.Mono;

public interface IProductPersistencePort {

    Mono<Void> save(Product product);

    Mono<Boolean> existsProductByNameAndBranchId(String productName, Long branchId);

    Mono<Void> deleteByIdAndBranchId(Long productId, Long branchId);

    Mono<Boolean> existsByIdAndBranchId(Long productId, Long branchId);
}
