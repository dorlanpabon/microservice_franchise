package com.pragma.franchise.infrastructure.adapters.jpa.adapter;

import com.pragma.franchise.domain.model.Product;
import com.pragma.franchise.infrastructure.adapters.jpa.entity.ProductEntity;
import com.pragma.franchise.infrastructure.adapters.jpa.mapper.IProductEntityMapper;
import com.pragma.franchise.infrastructure.adapters.jpa.repository.IProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class ProductAdapterTest {
    @Mock
    IProductRepository productRepository;
    @Mock
    IProductEntityMapper productEntityMapper;
    @InjectMocks
    ProductAdapter productAdapter;
    @Spy
    Product product;
    @Spy
    ProductEntity productEntity;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product.setName("name");
        product.setStock(50);
        product.setBranchId(2L);

        productEntity.setName("name");
        productEntity.setStock(50);
        productEntity.setBranchId(2L);
    }

    @Test
    void testSave() {
        when(productEntityMapper.toEntity(product)).thenReturn(productEntity);
        when(productRepository.save(productEntity)).thenReturn(Mono.just(productEntity));

        Mono<Void> result = productAdapter.save(product);

        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();

        verify(productEntityMapper, times(1)).toEntity(product);
    }

    @Test
    void testExistsProductByNameAndBranchId() {
        when(productRepository.existsByNameAndBranchId("name", 2L)).thenReturn(Mono.just(true));

        Mono<Boolean> result = productAdapter.existsProductByNameAndBranchId("name", 2L);

        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void testDeleteByIdAndBranchId() {
        when(productRepository.deleteByIdAndBranchId(1L, 2L)).thenReturn(Mono.empty());

        Mono<Void> result = productAdapter.deleteByIdAndBranchId(1L, 2L);

        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void testExistsByIdAndBranchId() {
        when(productRepository.existsByIdAndBranchId(1L, 2L)).thenReturn(Mono.just(true));

        Mono<Boolean> result = productAdapter.existsByIdAndBranchId(1L, 2L);

        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void testFindByIdAndBranchId() {
        when(productRepository.findByIdAndBranchId(1L, 2L)).thenReturn(Mono.just(productEntity));
        when(productEntityMapper.toDomain(productEntity)).thenReturn(product);

        Mono<Product> result = productAdapter.findByIdAndBranchId(1L, 2L);

        StepVerifier.create(result)
                .expectNext(product)
                .verifyComplete();
    }

    @Test
    void testUpdateStock() {
        when(productRepository.findByIdAndBranchId(1L, 2L)).thenReturn(Mono.just(productEntity));
        when(productRepository.save(productEntity)).thenReturn(Mono.just(productEntity));

        Mono<Void> result = productAdapter.updateStock(1L, 2L, 100);

        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();

        Assertions.assertEquals(100, productEntity.getStock());
    }

    @Test
    void testFindMaxStockProductByBranchForFranchise() {
        when(productRepository.findMaxStockProductByBranchForFranchise(1L)).thenReturn(Flux.just(productEntity));
        when(productEntityMapper.toDomain(productEntity)).thenReturn(product);

        Flux<Product> result = productAdapter.findMaxStockProductByBranchForFranchise(1L);

        StepVerifier.create(result)
                .expectNext(product)
                .verifyComplete();

        verify(productRepository, times(1)).findMaxStockProductByBranchForFranchise(1L);
    }
}