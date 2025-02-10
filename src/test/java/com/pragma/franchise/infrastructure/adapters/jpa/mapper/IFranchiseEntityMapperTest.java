package com.pragma.franchise.infrastructure.adapters.jpa.mapper;

import com.pragma.franchise.domain.model.Franchise;
import com.pragma.franchise.infrastructure.adapters.jpa.entity.FranchiseEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class IFranchiseEntityMapperTest {

    private final IFranchiseEntityMapper mapper = Mappers.getMapper(IFranchiseEntityMapper.class);

    @Test
    void testToEntity() {
        Franchise franchise = new Franchise();
        franchise.setId(1L);
        franchise.setName("Tech Franchise");

        FranchiseEntity franchiseEntity = mapper.toEntity(franchise);

        assertNotNull(franchiseEntity);
        assertEquals(franchise.getId(), franchiseEntity.getId());
        assertEquals(franchise.getName(), franchiseEntity.getName());
    }
}
