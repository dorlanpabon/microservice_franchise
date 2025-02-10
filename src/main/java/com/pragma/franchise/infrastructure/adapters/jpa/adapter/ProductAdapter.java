package com.pragma.franchise.infrastructure.adapters.jpa.adapter;

import com.pragma.franchise.domain.api.IProductPersistencePort;
import com.pragma.franchise.domain.model.Product;
import com.pragma.franchise.infrastructure.adapters.jpa.mapper.IProductEntityMapper;
import com.pragma.franchise.infrastructure.adapters.jpa.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ProductAdapter implements IProductPersistencePort {

    private final IProductRepository productRepository;
    private final IProductEntityMapper productEntityMapper;

    @Override
    public Mono<Void> save(Product product) {
        return Mono.just(product)
                .map(productEntityMapper::toEntity)
                .flatMap(productRepository::save)
                .then();
    }

    @Override
    public Mono<Boolean> existsProductByNameAndBranchId(String productName, Long branchId) {
        return productRepository.existsByNameAndBranchId(productName, branchId);
    }

    @Override
    public Mono<Void> deleteByIdAndBranchId(Long productId, Long branchId) {
        return productRepository.deleteByIdAndBranchId(productId, branchId).then();
    }

    @Override
    public Mono<Boolean> existsByIdAndBranchId(Long productId, Long branchId) {
        return productRepository.existsByIdAndBranchId(productId, branchId);
    }

    @Override
    public Mono<Product> findByIdAndBranchId(Long productId, Long branchId) {
        return productRepository.findByIdAndBranchId(productId, branchId)
                .map(productEntityMapper::toDomain);
    }

    @Override
    public Mono<Void> updateStock(Long productId, Long branchId, Integer newStock) {
        return productRepository.findByIdAndBranchId(productId, branchId)
                .map(product -> {
                    product.setStock(newStock);
                    return product;
                })
                .flatMap(productRepository::save)
                .then();
    }
}
