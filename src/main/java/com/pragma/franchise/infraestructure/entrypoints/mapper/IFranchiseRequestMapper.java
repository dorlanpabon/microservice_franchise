package com.pragma.franchise.infraestructure.entrypoints.mapper;

import com.pragma.franchise.domain.model.Franchise;
import com.pragma.franchise.infraestructure.entrypoints.dto.request.FranchiseRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface IFranchiseRequestMapper {

    Franchise toDomain(FranchiseRequestDto franchiseRequestDto);

}
