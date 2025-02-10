package com.pragma.franchise.infrastructure.entrypoints.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BranchRequestDto {

    @Schema(description = "Branch name", example = "Branch 1")
    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name must have a maximum of 50 characters")
    private String name;

    @Schema(description = "Franchise ID", example = "1")
    @NotNull(message = "Franchise ID is required")
    private Long franchiseId;
}