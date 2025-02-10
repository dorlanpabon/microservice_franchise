package com.pragma.franchise.infrastructure.entrypoints.mapper;

import com.pragma.franchise.domain.model.Franchise;
import com.pragma.franchise.infrastructure.entrypoints.dto.request.FranchiseRequestDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class IFranchiseRequestMapperTest {

    private final IFranchiseRequestMapper mapper = Mappers.getMapper(IFranchiseRequestMapper.class);

    @Test
    void testToDomain() {
        FranchiseRequestDto franchiseRequestDto = new FranchiseRequestDto();
        franchiseRequestDto.setName("Tech Franchise");

        Franchise franchise = mapper.toDomain(franchiseRequestDto);

        assertNotNull(franchise);
        assertEquals(franchiseRequestDto.getName(), franchise.getName());
    }
}
