package com.mycompany.app.residential;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssessmentClassTest {
    AssessmentClass testAssessmentClass;

    @BeforeEach
    void setUp() {
        testAssessmentClass = new AssessmentClass("Residential",100);
    }

    @Test
    void getAssessmentClassName() {
        String expected = "Residential";
        assertEquals(expected,testAssessmentClass.getAssessmentClassName());
    }

    @Test
    void testToString() {
        String expected = "Residential 100.0%";
        assertEquals(expected,testAssessmentClass.toString());
    }

    @Test
    void testEquals() {
        AssessmentClass expected = new AssessmentClass("Residential", 100);
        assertEquals(expected,testAssessmentClass);
    }

    @Test
    void testHashCode() {
        AssessmentClass expected = new AssessmentClass("Residential", 100);
        assertEquals(expected.hashCode(),testAssessmentClass.hashCode());
    }
}