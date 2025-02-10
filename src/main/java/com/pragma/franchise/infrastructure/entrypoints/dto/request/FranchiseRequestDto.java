package com.pragma.franchise.infrastructure.entrypoints.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FranchiseRequestDto {

    @Schema(description = "Franchise name", example = "McDonalds")
    @NotNull(message = "Franchise name is required")
    @NotBlank(message = "Franchise name is required")
    @Size(max = 50, message = "Franchise name must have a maximum of 50 characters")
    private String name;

}
