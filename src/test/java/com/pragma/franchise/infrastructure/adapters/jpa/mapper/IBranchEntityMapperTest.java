package com.pragma.franchise.infrastructure.adapters.jpa.mapper;

import com.pragma.franchise.domain.model.Branch;
import com.pragma.franchise.infrastructure.adapters.jpa.entity.BranchEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class IBranchEntityMapperTest {

    private final IBranchEntityMapper mapper = Mappers.getMapper(IBranchEntityMapper.class);

    @Test
    void testToEntity() {
        Branch branch = new Branch();
        branch.setId(1L);
        branch.setName("Sucursal Norte");
        branch.setFranchiseId(100L);

        BranchEntity branchEntity = mapper.toEntity(branch);

        assertNotNull(branchEntity);
        assertEquals(branch.getId(), branchEntity.getId());
        assertEquals(branch.getName(), branchEntity.getName());
        assertEquals(branch.getFranchiseId(), branchEntity.getFranchiseId());
    }

    @Test
    void testToDomain() {
        BranchEntity branchEntity = new BranchEntity();
        branchEntity.setId(1L);
        branchEntity.setName("Sucursal Norte");
        branchEntity.setFranchiseId(100L);

        Branch branch = mapper.toDomain(branchEntity);

        assertNotNull(branch);
        assertEquals(branchEntity.getId(), branch.getId());
        assertEquals(branchEntity.getName(), branch.getName());
        assertEquals(branchEntity.getFranchiseId(), branch.getFranchiseId());
    }
}
