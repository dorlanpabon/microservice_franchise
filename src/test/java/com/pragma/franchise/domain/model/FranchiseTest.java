package com.pragma.franchise.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FranchiseTest {

    @Mock
    private List<Branch> subsidiaries;

    @InjectMocks
    private Franchise franchise;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSetAndGetId() {
        franchise.setId(1L);
        assertEquals(1L, franchise.getId());
    }

    @Test
    void testSetAndGetName() {
        franchise.setName("Fast Food Co.");
        assertEquals("Fast Food Co.", franchise.getName());
    }

    @Test
    void testSetAndGetSubsidiaries() {
        franchise.setSubsidiaries(subsidiaries);
        assertEquals(subsidiaries, franchise.getSubsidiaries());
    }

    @Test
    void testSubsidiariesMockedBehavior() {
        when(subsidiaries.size()).thenReturn(3);
        franchise.setSubsidiaries(subsidiaries);

        assertEquals(3, franchise.getSubsidiaries().size());
        verify(subsidiaries, times(1)).size();
    }
}
