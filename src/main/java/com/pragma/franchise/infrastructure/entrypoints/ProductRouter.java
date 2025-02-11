package com.pragma.franchise.infrastructure.entrypoints;

import com.pragma.franchise.infrastructure.entrypoints.dto.request.ProductRequestDto;
import com.pragma.franchise.infrastructure.entrypoints.dto.request.ProductStockUpdateDto;
import com.pragma.franchise.infrastructure.entrypoints.dto.response.ProductStockResponseDto;
import com.pragma.franchise.infrastructure.entrypoints.handler.interfaces.IProductHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@Tag(name = "Product", description = "Product operations")
@Slf4j
public class ProductRouter {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/product",
                    beanClass = IProductHandler.class,
                    beanMethod = "addProduct",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            operationId = "addProduct",
                            summary = "Add a new product to a branch",
                            requestBody = @RequestBody(
                                    required = true,
                                    content = @Content(
                                            schema = @Schema(implementation = ProductRequestDto.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "201", description = "Product added", content = @Content),
                                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
                                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
                            }
                    )
            ),
            @RouterOperation(
                    path = "/product/{productId}/branch/{branchId}",
                    beanClass = IProductHandler.class,
                    beanMethod = "deleteProduct",
                    method = RequestMethod.DELETE,
                    operation = @Operation(
                            operationId = "deleteProduct",
                            summary = "Delete a product from a branch",
                            parameters = {
                                    @Parameter(name = "productId", required = true, schema = @Schema(type = "integer"), in = ParameterIn.PATH),
                                    @Parameter(name = "branchId", required = true, schema = @Schema(type = "integer"), in = ParameterIn.PATH)
                            },
                            responses = {
                                    @ApiResponse(responseCode = "204", description = "Product deleted", content = @Content),
                                    @ApiResponse(responseCode = "404", description = "Product not found", content = @Content),
                                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
                            }
                    )
            ),
            @RouterOperation(
                    path = "/product/{productId}/branch/{branchId}/stock",
                    beanClass = IProductHandler.class,
                    beanMethod = "updateStock",
                    method = RequestMethod.PATCH,
                    operation = @Operation(
                            operationId = "updateStock",
                            summary = "Update product stock",
                            requestBody = @RequestBody(
                                    required = true,
                                    content = @Content(
                                            schema = @Schema(implementation = ProductStockUpdateDto.class)
                                    )
                            ),
                            parameters = {
                                    @Parameter(name = "productId", required = true, schema = @Schema(type = "integer"), in = ParameterIn.PATH),
                                    @Parameter(name = "branchId", required = true, schema = @Schema(type = "integer"), in = ParameterIn.PATH)
                            },
                            responses = {
                                    @ApiResponse(responseCode = "204", description = "Stock updated", content = @Content),
                                    @ApiResponse(responseCode = "404", description = "Product not found", content = @Content),
                                    @ApiResponse(responseCode = "400", description = "Invalid stock change", content = @Content),
                                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
                            }
                    )
            ),
            @RouterOperation(
                    path = "/product/franchise/{franchiseId}/max-stock",
                    beanClass = IProductHandler.class,
                    beanMethod = "getMaxStockProductByBranchForFranchise",
                    method = RequestMethod.GET,
                    operation = @Operation(
                            operationId = "getMaxStockProductByBranchForFranchise",
                            summary = "Get product with max stock per branch for a franchise",
                            parameters = {
                                    @Parameter(name = "franchiseId", required = true, schema = @Schema(type = "integer"), in = ParameterIn.PATH)
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Products retrieved", content = @Content(schema = @Schema(implementation = ProductStockResponseDto.class))),
                                    @ApiResponse(responseCode = "404", description = "Franchise not found", content = @Content),
                                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
                            }
                    )
            ),
            @RouterOperation(
                    path = "/product/{id}",
                    beanClass = IProductHandler.class,
                    beanMethod = "updateProductName",
                    method = RequestMethod.PATCH,
                    operation = @Operation(
                            operationId = "updateProductName",
                            summary = "Update product name",
                            parameters = {
                                    @Parameter(name = "id", required = true, schema = @Schema(type = "integer"), in = ParameterIn.PATH),
                                    @Parameter(name = "newName", required = true, schema = @Schema(type = "string"))
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Product name updated", content = @Content),
                                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
                                    @ApiResponse(responseCode = "404", description = "Product not found", content = @Content),
                                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> productRoutes(IProductHandler productHandler) {
        return RouterFunctions.route()
                .POST("/product", productHandler::addProduct)
                .DELETE("/product/{productId}/branch/{branchId}", productHandler::deleteProduct)
                .PATCH("/product/{productId}/branch/{branchId}/stock", productHandler::updateStock)
                .GET("/product/franchise/{franchiseId}/max-stock", productHandler::getMaxStockProductByBranchForFranchise)
                .PATCH("/product/{id}", productHandler::updateProductName)
                .build();
    }
}
