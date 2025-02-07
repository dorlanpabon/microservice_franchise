package com.pragma.franchise.infraestructure.entrypoints;

import com.pragma.franchise.infraestructure.entrypoints.dto.request.FranchiseRequestDto;
import com.pragma.franchise.infraestructure.entrypoints.handler.interfaces.IFranchiseHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @Operation(summary = "Create a new franchise")
    @PostMapping("/")
    public Mono<Void> createFranchise(@Valid @RequestBody FranchiseRequestDto franchiseRequestDto) {
        return franchiseHandler.createFranchise(franchiseRequestDto).then();
    }

}
