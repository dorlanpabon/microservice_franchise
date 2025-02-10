package com.pragma.franchise.infrastructure.adapters.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductWithBranchNameDto {
    private Long id;
    private String name;
    private Long branchId;
    private Integer stock;
    private String branchName;
}
