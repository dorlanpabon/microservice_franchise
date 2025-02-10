package com.pragma.franchise.infrastructure.entrypoints.handler;

import com.pragma.franchise.domain.spi.IProductServicePort;
import com.pragma.franchise.infrastructure.entrypoints.dto.request.ProductRequestDto;
import com.pragma.franchise.infrastructure.entrypoints.handler.interfaces.IProductHandler;
import com.pragma.franchise.infrastructure.entrypoints.mapper.IProductRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductHandler implements IProductHandler {

    private final IProductServicePort productServicePort;
    private final IProductRequestMapper productRequestMapper;

    @Override
    public Mono<Void> addProduct(ProductRequestDto productRequestDto) {
        return Mono.just(productRequestDto)
                .map(productRequestMapper::toDomain)
                .flatMap(productServicePort::saveProduct)
                .then();
    }
}
