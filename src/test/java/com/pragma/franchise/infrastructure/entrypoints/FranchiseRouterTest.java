package com.pragma.franchise.infrastructure.entrypoints;

import com.pragma.franchise.infrastructure.entrypoints.dto.request.FranchiseRequestDto;
import com.pragma.franchise.infrastructure.entrypoints.handler.interfaces.IFranchiseHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class FranchiseRouterTest {

    private WebTestClient webTestClient;
    private IFranchiseHandler franchiseHandler;

    @BeforeEach
    void setUp() {
        franchiseHandler = Mockito.mock(IFranchiseHandler.class);
        FranchiseRouter franchiseRouter = new FranchiseRouter();
        RouterFunction<ServerResponse> routerFunction = franchiseRouter.franchiseRoutes(franchiseHandler);
        webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();
    }

    @Test
    void createFranchise_ShouldReturn201() {
        FranchiseRequestDto requestDto = new FranchiseRequestDto();
        requestDto.setName("Franchise 1");

        when(franchiseHandler.createFranchise(any())).thenReturn(ServerResponse.created(null).build());

        webTestClient.post()
                .uri("/franchise")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDto)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void updateFranchiseName_ShouldReturn200() {
        when(franchiseHandler.updateFranchiseName(any())).thenReturn(ServerResponse.ok().build());

        webTestClient.patch()
                .uri(uriBuilder -> uriBuilder
                        .path("/franchise")
                        .queryParam("franchiseId", "1")
                        .queryParam("newName", "Updated Franchise")
                        .build())
                .exchange()
                .expectStatus().isOk();
    }
}
