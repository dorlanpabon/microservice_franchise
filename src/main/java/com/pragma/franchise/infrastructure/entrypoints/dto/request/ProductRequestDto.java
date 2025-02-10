package com.pragma.franchise.infrastructure.entrypoints.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDto {

    @Schema(description = "Product name", example = "Product 1")
    @NotBlank(message = "Product name is required")
    private String name;

    @Schema(description = "Branch ID", example = "1")
    @NotNull(message = "Branch ID is required")
    private Long branchId;

    @Schema(description = "Product price", example = "1000")
    @Min(value = 0, message = "Stock must be non-negative")
    private Integer stock;
}
