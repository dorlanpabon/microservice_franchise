package com.pragma.franchise.infrastructure.entrypoints.handler.interfaces;

import com.pragma.franchise.infrastructure.entrypoints.dto.request.ProductRequestDto;
import com.pragma.franchise.infrastructure.entrypoints.dto.request.ProductStockUpdateDto;
import com.pragma.franchise.infrastructure.entrypoints.dto.response.ProductStockResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductHandler {
    Mono<Void> addProduct(ProductRequestDto productRequestDto);

    Mono<Void> deleteProduct(Long productId, Long branchId);

    Mono<Void> updateStock(Long productId, Long branchId, ProductStockUpdateDto stockUpdateDto);

    Flux<ProductStockResponseDto> getMaxStockProductByBranchForFranchise(Long franchiseId);

}
