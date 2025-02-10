package com.pragma.franchise.infrastructure.entrypoints;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
@Tag(name = "Health", description = "Health check")
public class HealtyController {

    @Operation(summary = "Health check")
    @GetMapping
    public String health() {
        return "OK";
    }

}
