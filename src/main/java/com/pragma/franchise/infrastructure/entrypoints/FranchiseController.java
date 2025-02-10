package com.pragma.franchise.infrastructure.entrypoints;

import com.pragma.franchise.infrastructure.entrypoints.dto.request.FranchiseRequestDto;
import com.pragma.franchise.infrastructure.entrypoints.handler.interfaces.IFranchiseHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/franchise")
@Tag(name = "Franchise", description = "Franchise operations")
public class FranchiseController {

    private final IFranchiseHandler franchiseHandler;

    @Operation(summary = "Create a new franchise", responses = {
            @ApiResponse(responseCode = "201", description = "Franchise created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/")
    public Mono<ResponseEntity<Void>> createFranchise(@Valid @RequestBody FranchiseRequestDto franchiseRequestDto) {
        return franchiseHandler.createFranchise(franchiseRequestDto)
                .then(Mono.just(ResponseEntity.status(HttpStatus.CREATED).build()));
    }

}
