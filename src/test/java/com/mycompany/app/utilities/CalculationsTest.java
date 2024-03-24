package com.mycompany.app.utilities;

import com.mycompany.app.properties.Coordinates;
import com.mycompany.app.properties.Neighbourhood;
import com.mycompany.app.residential.AssessmentClass;
import com.mycompany.app.residential.Residence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalculateDistanceTest {
    List<Residence> residences;

    @BeforeEach
    void setUp() {
        List<AssessmentClass> assessmentClassList = new ArrayList<>();
        assessmentClassList.add(new AssessmentClass("Residential",100));

        Residence testResidence1 = new Residence(1234,
                assessmentClassList,10000, new Neighbourhood("Neighbourhood","Ward"),
                new Coordinates(53.5707284,-113.3907046));

        Residence testResidence2 = new Residence(1234,
                assessmentClassList,20000, new Neighbourhood("Neighbourhood","Ward"),
                new Coordinates(53.5707284,-113.3907046));

        residences = new ArrayList<>();

        residences.add(testResidence1);
        residences.add(testResidence2);

    }

    @Test
    void calculateDistance() {
        Coordinates coordinates1 = new Coordinates(53.5707284,-113.3907046);
        Coordinates coordinates2 = new Coordinates(53.63235491366557,-113.43446135956096);
        Double expected = 7.435952917449862;
        assertEquals(expected, Calculations.CalculateDistance(coordinates1,coordinates2));

    }

    @Test
    void averageAssessmentValue(){
        double distance = 8.0;
        Coordinates coordinates = new Coordinates(53.63235491366557,-113.43446135956096);

        String average = Calculations.CalculateAverageAssessmentValue(residences,distance,coordinates);

        String expected = "$15,000.00";

        assertEquals(expected,average);

    }
}
