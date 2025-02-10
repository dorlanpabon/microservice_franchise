package com.pragma.franchise.domain.api;

import com.pragma.franchise.domain.model.Product;
import reactor.core.publisher.Mono;

public interface IProductPersistencePort {

    Mono<Void> save(Product product);

}
