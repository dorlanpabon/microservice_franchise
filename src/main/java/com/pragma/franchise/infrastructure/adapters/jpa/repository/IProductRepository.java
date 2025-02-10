package com.pragma.franchise.infrastructure.adapters.jpa.repository;

import com.pragma.franchise.infrastructure.adapters.jpa.entity.ProductEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface IProductRepository extends ReactiveCrudRepository<ProductEntity, Long> {

    Mono<Boolean> existsByNameAndBranchId(String productName, Long branchId);

    Mono<Void> deleteByIdAndBranchId(Long id, Long branchId);

    Mono<Boolean> existsByIdAndBranchId(Long id, Long branchId);

    Mono<ProductEntity> findByIdAndBranchId(Long productId, Long branchId);
}
