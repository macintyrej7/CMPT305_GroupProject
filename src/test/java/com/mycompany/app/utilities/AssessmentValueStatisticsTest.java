package com.mycompany.app.utilities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssessmentValueStatisticsTest {
    AssessmentValueStatistics testAssessmentValueStatistics;

    @BeforeEach
    void setup(){
        testAssessmentValueStatistics = new AssessmentValueStatistics(1.0,2.0, 3.0, 4.0, 1);
    }

    @Test
    void getAverage() {
        Double expected = 1.0;
        assertEquals(expected,testAssessmentValueStatistics.getAverage());
    }

    @Test
    void getMax() {
        Double expected = 2.0;
        assertEquals(expected,testAssessmentValueStatistics.getMax());
    }

    @Test
    void getMin() {
        Double expected = 3.0;
        assertEquals(expected,testAssessmentValueStatistics.getMin());
    }

    @Test
    void getMedian() {
        Double expected = 4.0;
        assertEquals(expected,testAssessmentValueStatistics.getMedian());
    }

    @Test
    void testGetSize(){
       int expected = 1;
       assertEquals(expected,testAssessmentValueStatistics.getSize());
    }


    @Test
    void testToString() {
        String expected = "average: $1.00\n" +
        "max: $2.00\n" +
        "min: $3.00\n" +
        "median: $4.00\n" +
        "number of residences: 1";
        assertEquals(expected,testAssessmentValueStatistics.toString());
    }

    @Test
    void testEquals() {
        AssessmentValueStatistics expected = new AssessmentValueStatistics(1.0,2.0, 3.0, 4.0, 1);
        assertEquals(expected,testAssessmentValueStatistics);

    }

    @Test
    void testHashCode() {
        AssessmentValueStatistics expected = new AssessmentValueStatistics(1.0,2.0, 3.0, 4.0, 1);
        assertEquals(expected.hashCode(),testAssessmentValueStatistics.hashCode());
    }
}