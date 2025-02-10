package com.pragma.franchise.infrastructure.entrypoints;

import com.pragma.franchise.infrastructure.entrypoints.dto.request.ProductRequestDto;
import com.pragma.franchise.infrastructure.entrypoints.dto.request.ProductStockUpdateDto;
import com.pragma.franchise.infrastructure.entrypoints.dto.response.ProductStockResponseDto;
import com.pragma.franchise.infrastructure.entrypoints.handler.interfaces.IProductHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
@Tag(name = "Product", description = "Product operations")
public class ProductController {

    private final IProductHandler productHandler;

    @Operation(summary = "Add a new product to a branch", responses = {
            @ApiResponse(responseCode = "201", description = "Product added"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/")
    public Mono<ResponseEntity<Void>> addProduct(@Valid @RequestBody ProductRequestDto productRequestDto) {
        return productHandler.addProduct(productRequestDto)
                .then(Mono.just(ResponseEntity.status(HttpStatus.CREATED).build()));
    }


    @Operation(summary = "Delete a product from a branch", responses = {
            @ApiResponse(responseCode = "204", description = "Product deleted"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{productId}/branch/{branchId}")
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable Long productId, @PathVariable Long branchId) {
        return productHandler.deleteProduct(productId, branchId)
                .then(Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).build()));
    }


    @Operation(summary = "Update product stock", responses = {
            @ApiResponse(responseCode = "204", description = "Stock updated"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid stock change"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/{productId}/branch/{branchId}/stock")
    public Mono<ResponseEntity<Void>> updateStock(@PathVariable Long productId, @PathVariable Long branchId,
                                                  @Valid @RequestBody ProductStockUpdateDto stockUpdateDto) {
        return productHandler.updateStock(productId, branchId, stockUpdateDto)
                .then(Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).build()));
    }


    @Operation(summary = "Get product with max stock per branch for a franchise", responses = {
            @ApiResponse(responseCode = "200", description = "Products retrieved", useReturnTypeSchema = false),
            @ApiResponse(responseCode = "404", description = "Franchise not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),
    })
    @GetMapping("/franchise/{franchiseId}/max-stock")
    public ResponseEntity<Flux<ProductStockResponseDto>> getMaxStockProductByBranchForFranchise(
            @PathVariable Long franchiseId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productHandler.getMaxStockProductByBranchForFranchise(franchiseId));
    }

    @Operation(summary = "Update product name", responses = {
            @ApiResponse(responseCode = "200", description = "Product name updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/{id}")
    public Mono<ResponseEntity<Void>> updateProductName(@PathVariable Long id, @RequestParam String newName) {
        return productHandler.updateProductName(id, newName)
                .then(Mono.just(ResponseEntity.ok().build()));
    }


}
