package com.mycompany.app.schools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class GradeListTest {
    GradeList testGradeList;
    @BeforeEach
    void setUp() {
        testGradeList = new GradeList(Arrays.asList("K", "1", "2", "3"));
    }

    @Test
    void getGradeList() {
        GradeList expected = new GradeList(Arrays.asList("K", "1", "2", "3"));
        assertEquals(expected, testGradeList);

    }

    @Test
    void testToString() {
        String expected = "K-3";
        assertEquals(expected, testGradeList.toString());

        GradeList testGradeList2 = new GradeList(Arrays.asList(""));
        String expected2 = "No grades";
        assertEquals(expected2, testGradeList2.toString());
    }

    @Test
    void testEquals() {
        GradeList expected = new GradeList(Arrays.asList("K", "1", "2", "3"));
        assertEquals(expected, testGradeList);
    }

    @Test
    void testHashCode() {
        GradeList expected = new GradeList(Arrays.asList("K", "1", "2", "3"));
        assertEquals(expected.hashCode(), testGradeList.hashCode());
    }
}