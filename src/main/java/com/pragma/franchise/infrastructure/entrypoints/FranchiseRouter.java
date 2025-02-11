package com.pragma.franchise.infrastructure.entrypoints;

import com.pragma.franchise.infrastructure.entrypoints.dto.request.FranchiseRequestDto;
import com.pragma.franchise.infrastructure.entrypoints.handler.interfaces.IFranchiseHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@Tag(name = "Franchise", description = "Franchise operations")
@Slf4j
public class FranchiseRouter {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/franchise",
                    beanClass = IFranchiseHandler.class,
                    beanMethod = "createFranchise",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            operationId = "createFranchise",
                            summary = "Create a new franchise",
                            description = "Creates a new franchise",
                            requestBody = @RequestBody(
                                    required = true,
                                    content = @Content(
                                            schema = @Schema(implementation = FranchiseRequestDto.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "201", description = "Franchise created", content = @Content),
                                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
                                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
                            }
                    )
            ),
            @RouterOperation(
                    path = "/franchise",
                    beanClass = IFranchiseHandler.class,
                    beanMethod = "updateFranchiseName",
                    method = RequestMethod.PATCH,
                    operation = @Operation(
                            operationId = "updateFranchiseName",
                            summary = "Update franchise name",
                            description = "Updates the name of a franchise",
                            parameters = {
                                    @Parameter(name = "franchiseId", description = "Franchise ID", required = true,
                                            schema = @Schema(type = "integer")),
                                    @Parameter(name = "newName", description = "New franchise name", required = true,
                                            schema = @Schema(type = "string"))
                            },

                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Franchise name updated", content = @Content),
                                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
                                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> franchiseRoutes(IFranchiseHandler franchiseHandler) {
        return RouterFunctions.route()
                .POST("/franchise", franchiseHandler::createFranchise)
                .PATCH("/franchise", franchiseHandler::updateFranchiseName)
                .build();
    }

}
