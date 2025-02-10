package com.pragma.franchise.infrastructure.adapters.jpa.repository;

import com.pragma.franchise.infrastructure.adapters.jpa.dto.ProductWithBranchNameDto;
import com.pragma.franchise.infrastructure.adapters.jpa.entity.ProductEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductRepository extends ReactiveCrudRepository<ProductEntity, Long> {

    Mono<Boolean> existsByNameAndBranchId(String productName, Long branchId);

    Mono<Void> deleteByIdAndBranchId(Long id, Long branchId);

    Mono<Boolean> existsByIdAndBranchId(Long id, Long branchId);

    Mono<ProductEntity> findByIdAndBranchId(Long productId, Long branchId);

    @Query("""
        SELECT p.id, p.name, p.branch_id, p.stock, b.name AS branch_name
        FROM product p
        JOIN branch b ON p.branch_id = b.id
        WHERE b.franchise_id = :franchiseId
        AND p.stock = (SELECT MAX(p2.stock) FROM product p2 WHERE p2.branch_id = p.branch_id)
    """)
    Flux<ProductWithBranchNameDto> findMaxStockProductByBranchForFranchise(Long franchiseId);

}
