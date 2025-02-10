package com.pragma.franchise.infrastructure.adapters.jpa.mapper;

import com.pragma.franchise.domain.model.Branch;
import com.pragma.franchise.infrastructure.adapters.jpa.entity.BranchEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IBranchEntityMapper {
    BranchEntity toEntity(Branch branch);
}
