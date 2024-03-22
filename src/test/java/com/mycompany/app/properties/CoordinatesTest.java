package com.mycompany.app.properties;


import com.mycompany.app.properties.Coordinates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinatesTest {
    Coordinates testCoordinates;
    @BeforeEach
    void setUp() {
        testCoordinates = new Coordinates(123.123,456.456);
    }

    @Test
    void getLatitude() {
        double expected = 123.123;
        assertEquals(expected,testCoordinates.getLatitude());
    }

    @Test
    void getLongitude() {
        double expected = 456.456;
        assertEquals(expected,testCoordinates.getLongitude());
    }

    @Test
    void testEquals() {
        Coordinates expected = new Coordinates(123.123,456.456);
        assertEquals(expected,testCoordinates);
    }

    @Test
    void testHashCode() {
        Coordinates expected = new Coordinates(123.123,456.456);
        assertEquals(expected.hashCode(),testCoordinates.hashCode());
    }

    @Test
    void testToString() {
        String expected = "(123.123, 456.456)";
        assertEquals(expected,testCoordinates.toString());
    }
}