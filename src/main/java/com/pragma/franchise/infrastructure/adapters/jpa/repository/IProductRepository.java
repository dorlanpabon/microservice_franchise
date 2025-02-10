package com.pragma.franchise.infrastructure.adapters.jpa.repository;

import com.pragma.franchise.infrastructure.adapters.jpa.entity.ProductEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface IProductRepository extends ReactiveCrudRepository<ProductEntity, Long> {

    Mono<Boolean> existsByNameAndBranchId(String productName, Long branchId);
}
