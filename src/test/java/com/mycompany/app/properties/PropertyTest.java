package com.mycompany.app.properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertyTest {
    Property testProperty;
    @BeforeEach
    void setUp() {
        testProperty = new Property(new Address("123 fake street","90210"),
                new Coordinates(123.123,456.456),
                new Neighbourhood("Neighbourhood","ward"));


    }

    @Test
    void getAddress() {
        Address expected = new Address("123 fake street","90210");
        assertEquals(expected,testProperty.getAddress());
    }

    @Test
    void getLongitude() {
        Double expected = 456.456;
        assertEquals(expected,testProperty.getLongitude());
    }

    @Test
    void getLatitude() {
        Double expected = 123.123;
        assertEquals(expected,testProperty.getLatitude());
    }

    @Test
    void getCoordinates() {
        Coordinates expected = new Coordinates(123.123,456.456);
        assertEquals(expected,testProperty.getCoordinates());
    }

    @Test
    void testToString() {
        String expected = "Address = 123 fake street 90210\n" +
                "Coordinates = (123.123, 456.456)";
        assertEquals(expected,testProperty.toString());
    }

    @Test
    void testEquals() {
        Property expected = new Property(new Address("123 fake street","90210"),
                new Coordinates(123.123,456.456),
                new Neighbourhood("Neighbourhood","ward"));

        assertEquals(expected,testProperty);
    }

    @Test
    void testHashCode() {
        Property expected = new Property(new Address("123 fake street","90210"),
                new Coordinates(123.123,456.456),
                new Neighbourhood("Neighbourhood","ward"));

        assertEquals(expected.hashCode(),testProperty.hashCode());

    }


}