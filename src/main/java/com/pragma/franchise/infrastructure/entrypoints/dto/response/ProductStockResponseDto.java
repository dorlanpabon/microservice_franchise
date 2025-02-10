package com.pragma.franchise.infrastructure.entrypoints.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductStockResponseDto {

    @Schema(description = "Product id", example = "1")
    private Long productId;
    @Schema(description = "Product name", example = "Coca Cola")
    private String productName;
    @Schema(description = "Product stock", example = "100")
    private Integer stock;
    @Schema(description = "Branch id", example = "1")
    private Long branchId;
    @Schema(description = "Branch name", example = "Bogota")
    private String branchName;

}
