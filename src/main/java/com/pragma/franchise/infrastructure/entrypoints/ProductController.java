package com.pragma.franchise.infrastructure.entrypoints;

import com.pragma.franchise.infrastructure.entrypoints.dto.request.ProductRequestDto;
import com.pragma.franchise.infrastructure.entrypoints.handler.interfaces.IProductHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
}
