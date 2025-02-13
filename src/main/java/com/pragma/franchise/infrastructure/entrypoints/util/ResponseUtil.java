package com.pragma.franchise.infrastructure.entrypoints.util;

import com.pragma.franchise.infrastructure.entrypoints.dto.response.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class ResponseUtil {

    public static Mono<ServerResponse> buildErrorResponse(HttpStatus status, String message) {
        return ServerResponse.status(status)
                .bodyValue(ErrorResponseDto.builder()
                        .status(status.value())
                        .message(message)
                        .build());
    }

    ResponseUtil() {
        throw new IllegalStateException("Utility class");
    }
}
