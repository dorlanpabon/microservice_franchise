package com.pragma.franchise.infrastructure.entrypoints.handler;

import com.pragma.franchise.domain.spi.IProductServicePort;
import com.pragma.franchise.infrastructure.entrypoints.dto.request.ProductRequestDto;
import com.pragma.franchise.infrastructure.entrypoints.dto.request.ProductStockUpdateDto;
import com.pragma.franchise.infrastructure.entrypoints.dto.response.ProductStockResponseDto;
import com.pragma.franchise.infrastructure.entrypoints.handler.interfaces.IProductHandler;
import com.pragma.franchise.infrastructure.entrypoints.mapper.IProductRequestMapper;
import com.pragma.franchise.infrastructure.entrypoints.mapper.IProductResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductHandler implements IProductHandler {

    private final IProductServicePort productServicePort;
    private final IProductRequestMapper productRequestMapper;
    private final IProductResponseMapper productResponseMapper;

    @Override
    public Mono<Void> addProduct(ProductRequestDto productRequestDto) {
        return Mono.just(productRequestDto)
                .map(productRequestMapper::toDomain)
                .flatMap(productServicePort::saveProduct)
                .then();
    }

    @Override
    public Mono<Void> deleteProduct(Long productId, Long branchId) {
        return productServicePort.deleteProduct(productId, branchId);
    }

    @Override
    public Mono<Void> updateStock(Long productId, Long branchId, ProductStockUpdateDto stockUpdateDto) {
        return productServicePort.updateStock(productId, branchId, stockUpdateDto.getStockChange());
    }

    @Override
    public Flux<ProductStockResponseDto> getMaxStockProductByBranchForFranchise(Long franchiseId) {
        return productServicePort.getMaxStockProductByBranchForFranchise(franchiseId)
                .map(productResponseMapper::toResponse);
    }

    @Override
    public Mono<Void> updateProductName(Long productId, String newName) {
        return productServicePort.updateProductName(productId, newName);
    }

}
