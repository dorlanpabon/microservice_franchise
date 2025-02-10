package com.pragma.franchise.domain.spi;

import com.pragma.franchise.domain.model.Product;
import reactor.core.publisher.Mono;

public interface IProductServicePort {

    Mono<Void> saveProduct(Product product);

    Mono<Void> deleteProduct(Long productId, Long branchId);

    Mono<Void> updateStock(Long productId, Long branchId, Integer stockChange);
}
