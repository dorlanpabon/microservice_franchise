package com.pragma.franchise.infrastructure.entrypoints;

import com.pragma.franchise.infrastructure.entrypoints.dto.request.BranchRequestDto;
import com.pragma.franchise.infrastructure.entrypoints.handler.interfaces.IBranchHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@Tag(name = "Branch", description = "Branch operations")
public class BranchRouter {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/branch",
                    beanClass = IBranchHandler.class,
                    beanMethod = "createBranch",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            operationId = "createBranch",
                            summary = "Create a new branch",
                            description = "Creates a new branch",
                            requestBody = @RequestBody(
                                    required = true,
                                    content = @Content(
                                            schema = @Schema(implementation = BranchRequestDto.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "201", description = "Branch created", content = @Content),
                                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
                                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
                            }
                    )
            ),
            @RouterOperation(
                    path = "/branch",
                    beanClass = IBranchHandler.class,
                    beanMethod = "updateBranchName",
                    method = RequestMethod.PATCH,
                    operation = @Operation(
                            operationId = "updateBranchName",
                            summary = "Update branch name",
                            description = "Updates the name of a branch",
                            parameters = {
                                    @Parameter(name = "branchId", description = "Branch ID", required = true,
                                            schema = @Schema(type = "integer")),
                                    @Parameter(name = "newName", description = "New branch name", required = true,
                                            schema = @Schema(type = "string"))
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Branch name updated", content = @Content),
                                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
                                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> branchRoutes(IBranchHandler branchHandler) {
        return RouterFunctions.route()
                .POST("/branch", branchHandler::createBranch)
                .PATCH("/branch", branchHandler::updateBranchName)
                .build();
    }
}
