package com.pragma.franchise.infrastructure.entrypoints.mapper;

import com.pragma.franchise.domain.model.Branch;
import com.pragma.franchise.infrastructure.entrypoints.dto.request.BranchRequestDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class IBranchRequestMapperTest {

    private final IBranchRequestMapper mapper = Mappers.getMapper(IBranchRequestMapper.class);

    @Test
    void testToDomain() {
        BranchRequestDto branchRequestDto = new BranchRequestDto();
        branchRequestDto.setName("Sucursal Centro");
        branchRequestDto.setFranchiseId(100L);

        Branch branch = mapper.toDomain(branchRequestDto);

        assertNotNull(branch);
        assertEquals(branchRequestDto.getName(), branch.getName());
        assertEquals(branchRequestDto.getFranchiseId(), branch.getFranchiseId());
    }
}
