package com.pragma.franchise.infrastructure.entrypoints.handler;

import com.pragma.franchise.domain.model.Product;
import com.pragma.franchise.domain.spi.IProductServicePort;
import com.pragma.franchise.infrastructure.entrypoints.dto.request.ProductStockUpdateDto;
import com.pragma.franchise.infrastructure.entrypoints.dto.response.ProductStockResponseDto;
import com.pragma.franchise.infrastructure.entrypoints.mapper.IProductRequestMapper;
import com.pragma.franchise.infrastructure.entrypoints.mapper.IProductResponseMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.URI;
import java.util.Map;

import static org.mockito.Mockito.*;

class ProductHandlerTest {

    @Mock
    private IProductServicePort productServicePort;

    @Mock
    private IProductRequestMapper productRequestMapper;

    @Mock
    private IProductResponseMapper productResponseMapper;

    @InjectMocks
    private ProductHandler productHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeleteProduct_ShouldReturnNoContent() {
        ServerRequest request = MockServerRequest.builder()
                .pathVariable("productId", "1")
                .pathVariable("branchId", "1")
                .build();

        when(productServicePort.deleteProduct(1L, 1L)).thenReturn(Mono.empty());

        StepVerifier.create(productHandler.deleteProduct(request))
                .expectNextMatches(response -> response.statusCode().equals(HttpStatus.NO_CONTENT))
                .verifyComplete();

        verify(productServicePort, times(1)).deleteProduct(1L, 1L);
    }

    @Test
    void testUpdateStock_ShouldReturnNoContent() {
        ProductStockUpdateDto dto = new ProductStockUpdateDto();
        dto.setStockChange(5);

        ServerRequest request = MockServerRequest.builder()
                .pathVariable("productId", "1")
                .pathVariable("branchId", "1")
                .body(Mono.just(dto));

        when(productServicePort.updateStock(1L, 1L, 5)).thenReturn(Mono.empty());

        StepVerifier.create(productHandler.updateStock(request))
                .expectNextMatches(response -> response.statusCode().equals(HttpStatus.NO_CONTENT))
                .verifyComplete();

        verify(productServicePort, times(1)).updateStock(1L, 1L, 5);
    }

    @Test
    void testGetMaxStockProductByBranchForFranchise_ShouldReturnOk() {
        ProductStockResponseDto responseDto = new ProductStockResponseDto();
        responseDto.setProductId(1L);
        responseDto.setStock(50);

        ServerRequest request = MockServerRequest.builder()
                .pathVariable("franchiseId", "1")
                .build();

        Product product = new Product();
        product.setId(1L);
        product.setStock(50);
        product.setBranchId(1L);
        product.setName("Product");
        product.setBranchName("Branch");

        when(productServicePort.getMaxStockProductByBranchForFranchise(1L))
                .thenReturn(Flux.just(product));
        when(productResponseMapper.toResponse(any(Product.class))).thenReturn(responseDto);

        StepVerifier.create(productHandler.getMaxStockProductByBranchForFranchise(request))
                .expectNextMatches(response -> response.statusCode().equals(HttpStatus.OK))
                .verifyComplete();
    }

    @Test
    void testUpdateProductName_ShouldReturnOk() {
        ServerRequest request = MockServerRequest.builder()
                .pathVariable("id", "1")
                .queryParam("newName", "Updated Product")
                .build();

        when(productServicePort.updateProductName(1L, "Updated Product")).thenReturn(Mono.empty());

        StepVerifier.create(productHandler.updateProductName(request))
                .expectNextMatches(response -> response.statusCode().equals(HttpStatus.OK))
                .verifyComplete();

        verify(productServicePort, times(1)).updateProductName(1L, "Updated Product");
    }
}
