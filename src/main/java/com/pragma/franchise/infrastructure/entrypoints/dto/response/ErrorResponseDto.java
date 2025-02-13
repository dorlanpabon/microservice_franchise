package com.pragma.franchise.infrastructure.entrypoints.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDto {
    private int status;
    private String message;

    public ErrorResponseDto(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
    }
}