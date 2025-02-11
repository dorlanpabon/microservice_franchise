package com.pragma.franchise.infrastructure.entrypoints;

import com.pragma.franchise.infrastructure.entrypoints.dto.request.BranchRequestDto;
import com.pragma.franchise.infrastructure.entrypoints.handler.interfaces.IBranchHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class BranchRouterTest {

    private WebTestClient webTestClient;
    private IBranchHandler branchHandler;

    @BeforeEach
    void setUp() {
        branchHandler = Mockito.mock(IBranchHandler.class);
        BranchRouter branchRouter = new BranchRouter();
        RouterFunction<ServerResponse> routerFunction = branchRouter.branchRoutes(branchHandler);
        webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();
    }

    @Test
    void createBranch_ShouldReturn201() {
        BranchRequestDto requestDto = new BranchRequestDto();
        requestDto.setName("Branch 1");
        requestDto.setFranchiseId(1L);

        when(branchHandler.createBranch(any())).thenReturn(ServerResponse.created(null).build());

        webTestClient.post()
                .uri("/branch")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDto)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void updateBranchName_ShouldReturn200() {
        when(branchHandler.updateBranchName(any())).thenReturn(ServerResponse.ok().build());

        webTestClient.patch()
                .uri(uriBuilder -> uriBuilder
                        .path("/branch")
                        .queryParam("branchId", "1")
                        .queryParam("newName", "Updated Branch")
                        .build())
                .exchange()
                .expectStatus().isOk();
    }
}
