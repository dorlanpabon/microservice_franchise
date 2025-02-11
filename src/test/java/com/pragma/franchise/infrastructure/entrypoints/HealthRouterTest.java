package com.pragma.franchise.infrastructure.entrypoints;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

class HealthRouterTest {

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        HealthRouter healthRouter = new HealthRouter();
        RouterFunction<ServerResponse> routerFunction = healthRouter.healthRoute();
        webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();
    }

    @Test
    void testHealthRoute_ShouldReturnOK() {
        webTestClient.get()
                .uri("/health")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("OK");
    }
}
