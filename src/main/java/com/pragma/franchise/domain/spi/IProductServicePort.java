package com.pragma.franchise.domain.spi;

import com.pragma.franchise.domain.model.Product;
import reactor.core.publisher.Mono;

public interface IProductServicePort {

    Mono<Void> saveProduct(Product product);

}
