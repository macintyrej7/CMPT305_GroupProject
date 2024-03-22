package com.mycompany.app.utilities;

import com.mycompany.app.properties.Coordinates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculateDistanceTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void calculateDistance() {
        Coordinates coordinates1 = new Coordinates(53.5707284,-113.3907046);
        Coordinates coordinates2 = new Coordinates(53.63235491366557,-113.43446135956096);
        Double expected = 7.435952917449862;
        assertEquals(expected, Calculations.CalculateDistance(coordinates1,coordinates2));

    }
}