package com.pragma.franchise.infrastructure.entrypoints.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductStockUpdateDto {

    @Schema(description = "Stock change", example = "10")
    @NotNull
    private Integer stockChange;
}
