package com.pragma.franchise.infrastructure.entrypoints.mapper;

import com.pragma.franchise.domain.model.Branch;
import com.pragma.franchise.infrastructure.entrypoints.dto.request.BranchRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface IBranchRequestMapper {
    Branch toDomain(BranchRequestDto branchRequestDto);
}
