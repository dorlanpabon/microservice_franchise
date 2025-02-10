package com.pragma.franchise.domain.api;

import com.pragma.franchise.domain.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductPersistencePort {

    Mono<Void> save(Product product);

    Mono<Boolean> existsProductByNameAndBranchId(String productName, Long branchId);

    Mono<Void> deleteByIdAndBranchId(Long productId, Long branchId);

    Mono<Boolean> existsByIdAndBranchId(Long productId, Long branchId);

    Mono<Product> findByIdAndBranchId(Long productId, Long branchId);

    Mono<Void> updateStock(Long productId, Long branchId, Integer newStock);

    Flux<Product> findMaxStockProductByBranchForFranchise(Long franchiseId);

    Mono<Product> findById(Long productId);
}
