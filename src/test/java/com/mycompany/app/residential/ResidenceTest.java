package com.mycompany.app.residential;

import com.mycompany.app.properties.Coordinates;
import com.mycompany.app.properties.Neighbourhood;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResidenceTest {
    Residence testResidence;
    @BeforeEach
    void setUp() {
        List<AssessmentClass> assessmentClassList = new ArrayList<>();
        assessmentClassList.add(new AssessmentClass("Residential",100));
        testResidence = new Residence(1234,
                assessmentClassList,10000, new Neighbourhood("Neighbourhood","Ward"),
                new Coordinates(123.123,456.456));
    }
    @Test
    void getAccountNumber() {
        int expected = 1234;
        assertEquals(expected,testResidence.getAccountNumber());
    }

    @Test
    void getAssessementClassList() {
        List<AssessmentClass> expected = new ArrayList<>();
        expected.add(new AssessmentClass("Residential",100));

        assertEquals(expected,testResidence.getAssessementClassList());
    }

    @Test
    void getAssessedValue() {
        int expected = 10000;
        assertEquals(expected,testResidence.getAssessedValue());
    }

    @Test
    void testEquals() {
        List<AssessmentClass> assessmentClassList = new ArrayList<>();
        assessmentClassList.add(new AssessmentClass("Residential",100));
        Residence expected = new Residence(1234,
                assessmentClassList,10000, new Neighbourhood("Neighbourhood","Ward"),
                new Coordinates(123.123,456.456));

        assertEquals(expected,testResidence);
    }

    @Test
    void testHashCode() {
        List<AssessmentClass> assessmentClassList = new ArrayList<>();
        assessmentClassList.add(new AssessmentClass("Residential",100));
        Residence expected = new Residence(1234,
                assessmentClassList,10000, new Neighbourhood("Neighbourhood","Ward"),
                new Coordinates(123.123,456.456));

        assertEquals(expected.hashCode(),testResidence.hashCode());
    }

    @Test
    void testToString() {
        String expected = "Residence{accountNumber=1234, assessementClassList=[Residential 100.0%], assessedValue=10000}";

        assertEquals(expected,testResidence.toString());
    }

    @Test
    void testContainsAssessmentClass(){
        Boolean expected = true;
        assertEquals(expected,testResidence.containsAssessmentClass("Residential"));

        Boolean expected2 = false;
        assertEquals(expected2,testResidence.containsAssessmentClass("Industrial"));

    }


}