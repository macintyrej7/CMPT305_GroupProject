package com.mycompany.app.properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NeighbourhoodTest {
    Neighbourhood testNeighbourhood;
    @BeforeEach
    void setUp() {

        testNeighbourhood = new Neighbourhood("Neighbourhood","Ward");
    }

    @Test
    void getNeighbourhoodName() {
        String expected = "Neighbourhood";
        assertEquals(expected, testNeighbourhood.getNeighbourhoodName());


    }

    @Test
    void getWard() {
        String expected = "Ward";
        assertEquals(expected, testNeighbourhood.getWard());
    }

    @Test
    void testToString() {
        String expected = "Neighbourhood (Ward)";
        assertEquals(expected, testNeighbourhood.toString());
    }

    @Test
    void testEquals() {
        Neighbourhood expected = new Neighbourhood("Neighbourhood","Ward");
        assertEquals(expected,testNeighbourhood);
    }

    @Test
    void testHashCode() {
        Neighbourhood expected = new Neighbourhood("Neighbourhood","Ward");
        assertEquals(expected.hashCode(),testNeighbourhood.hashCode());
    }
}