package com.pragma.franchise.infrastructure.entrypoints;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class HealthRouter {

    @Bean
    public RouterFunction<ServerResponse> healthRoute() {
        return route(GET("/health"),
                request -> ServerResponse.ok().bodyValue("OK"));
    }
}
