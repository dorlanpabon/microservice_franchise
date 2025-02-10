package com.pragma.franchise.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.*;

class BranchTest {

    @Spy
    private Branch branch;

    @BeforeEach
    void setUp() {
        branch = new Branch();
    }

    @Test
    void testSetAndGetId() {
        branch.setId(1L);
        assertEquals(1L, branch.getId());
    }

    @Test
    void testSetAndGetName() {
        branch.setName("Sucursal Centro");
        assertEquals("Sucursal Centro", branch.getName());
    }

    @Test
    void testSetAndGetFranchiseId() {
        branch.setFranchiseId(100L);
        assertEquals(100L, branch.getFranchiseId());
    }
}
