package com.pragma.franchise.infrastructure.entrypoints;

import com.pragma.franchise.infrastructure.entrypoints.dto.request.ProductRequestDto;
import com.pragma.franchise.infrastructure.entrypoints.dto.request.ProductStockUpdateDto;
import com.pragma.franchise.infrastructure.entrypoints.dto.response.ProductStockResponseDto;
import com.pragma.franchise.infrastructure.entrypoints.handler.interfaces.IProductHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ProductRouterTest {

    private WebTestClient webTestClient;
    private IProductHandler productHandler;

    @BeforeEach
    void setUp() {
        productHandler = Mockito.mock(IProductHandler.class);
        ProductRouter productRouter = new ProductRouter();
        RouterFunction<ServerResponse> routerFunction = productRouter.productRoutes(productHandler);
        webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();
    }

    @Test
    void addProduct_ShouldReturn201() {
        ProductRequestDto requestDto = new ProductRequestDto();
        requestDto.setName("Product");
        requestDto.setBranchId(1L);
        requestDto.setStock(100);

        when(productHandler.addProduct(any())).thenReturn(ServerResponse.created(null).build());

        webTestClient.post()
                .uri("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDto)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void deleteProduct_ShouldReturn204() {
        when(productHandler.deleteProduct(any())).thenReturn(ServerResponse.noContent().build());

        webTestClient.delete()
                .uri("/product/1/branch/2")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void updateStock_ShouldReturn204() {
        ProductStockUpdateDto stockUpdateDto = new ProductStockUpdateDto();
        stockUpdateDto.setStockChange(100);

        when(productHandler.updateStock(any())).thenReturn(ServerResponse.noContent().build());

        webTestClient.patch()
                .uri("/product/1/branch/2/stock")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(stockUpdateDto)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void getMaxStockProductByBranchForFranchise_ShouldReturn200() {
        ProductStockResponseDto responseDto = new ProductStockResponseDto();
        responseDto.setProductId(1L);
        responseDto.setProductName("Product");
        responseDto.setStock(100);

        when(productHandler.getMaxStockProductByBranchForFranchise(any()))
                .thenReturn(ServerResponse.ok().body(Mono.just(responseDto), ProductStockResponseDto.class));

        webTestClient.get()
                .uri("/product/franchise/1/max-stock")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void updateProductName_ShouldReturn200() {
        when(productHandler.updateProductName(any())).thenReturn(ServerResponse.ok().build());

        webTestClient.patch()
                .uri(uriBuilder -> uriBuilder
                        .path("/product/1")
                        .queryParam("newName", "Updated Product")
                        .build())
                .exchange()
                .expectStatus().isOk();
    }
}
