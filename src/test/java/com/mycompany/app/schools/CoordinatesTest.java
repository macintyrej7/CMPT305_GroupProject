/**
 * Authors: Legan Hunter-Mutima, Brian Lin, Jason MacIntyre, Sankalp Shrivastav
 * Course: CMPT 305 AS01
 * Instructor: Dr. Indratmo
 * Assignment: Group project
 * Due date: ???
 * Last worked on: Mar 21, 2024
 * Program name:
 * Program description:
 */

package com.mycompany.app.schools;

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