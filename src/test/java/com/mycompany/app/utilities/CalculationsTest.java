package com.mycompany.app.utilities;

import com.mycompany.app.ImportResidences;
import com.mycompany.app.properties.Coordinates;
import com.mycompany.app.properties.Neighbourhood;
import com.mycompany.app.residential.AssessmentClass;
import com.mycompany.app.residential.Residence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

        String average = Calculations.calculateAverageAssessmentValue(residences,distance,coordinates);

        String expected = "$15,000.00";

        assertEquals(expected,average);

    }

    @Test
    void averageAssessmentValueWithDataset(){

        double distance = 2.0;

        Coordinates coordinates = new Coordinates(53.4638631,-113.5281877);


        String average = Calculations.calculateAverageAssessmentValue(residences,distance,coordinates);

        String expected = "$0.00";

        assertEquals(expected,average);

    }

    @Test
    void calculateMedianTest(){
        List<Double> doublesList = Arrays.asList(2.0, 3.0, 1.0);
        double median = Calculations.calculateMedian(doublesList);
        double expected = 2.0;

        assertEquals(expected, median);

    }

    @Test
    void AssessmentValueStatisticsTest() throws IOException {

        AssessmentValueStatistics testStatistics = Calculations.calculateAssessmentValueStatistics(residences,30.00,
                new Coordinates(53.518810045412266,-113.50219250052204));

        double testAverage = testStatistics.getAverage();
        double expectedAverage = 15000.0;
        assertEquals(expectedAverage,testAverage);

        double testMin = testStatistics.getMin();
        double expectedMin = 10000.0;
        assertEquals(expectedMin,testMin);

        double testMax = testStatistics.getMax();
        double expectedMax = 20000.0;
        assertEquals(expectedMax,testMax);

        double testMedian = testStatistics.getMedian();
        double expectedMedian = 15000.0;
        assertEquals(expectedMedian,testMedian);


    }
}
