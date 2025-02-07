package com.pragma.franchise.infraestructure.adapters.jpa.mapper;

import com.pragma.franchise.domain.model.Franchise;
import com.pragma.franchise.infraestructure.adapters.jpa.entity.FranchiseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IFranchiseEntityMapper {

    FranchiseEntity toEntity(Franchise franchise);

}
