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

import com.mycompany.app.properties.Address;
import com.mycompany.app.properties.Coordinates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SchoolTest {
    School testSchool;
    @BeforeEach
    void setUp() {

        List<String> gradeLevels = new ArrayList<>();
        gradeLevels.add("Elementary");

        testSchool = new School(1234,
                "Fake School",
                new Address("123","Fake Street"),
                new Coordinates(123.123,456.456),
                gradeLevels,
                true,
                true,
                "email@school.com",
                new PhoneNumber("1234567890"),
                "http://school.com",
                "Public"
        );



    }

    @Test
    void getSchoolNumber() {
        int exptected = 1234;
        assertEquals(exptected,testSchool.getSchoolNumber());
    }

    @Test
    void getSchoolName() {
        String expected = "Fake School";
        assertEquals(expected,testSchool.getSchoolName());
    }

    @Test
    void getSchoolAddress() {
        Address expected = new Address("123","Fake Street");
        assertEquals(expected,testSchool.getAddress());

    }

    @Test
    void getSchoolCoordinates() {
        Coordinates expected = new Coordinates(123.123,456.456);
        assertEquals(expected,testSchool.getCoordinates());
    }

    @Test
    void getSchooLGradeLevel() {
        List<String> testGradeLevels = new ArrayList<>();
        testGradeLevels.add("Elementary");

        assertEquals(testGradeLevels, testSchool.getSchoolGradeLevels());
    }

    @Test
    void isHasSpanish() {
        boolean expected = true;
        assertEquals(expected,testSchool.isSpanishBilingual());
    }

    @Test
    void isHasFrench() {
        boolean expected = true;
        assertEquals(expected,testSchool.isFrenchImmersion());
    }

    @Test
    void getEmail() {
        String expected = "email@school.com";
        assertEquals(expected,testSchool.getSchoolEmail());

    }

    @Test
    void getSchoolPhoneNumber() {
        PhoneNumber expected = new PhoneNumber("1234567890");
        assertEquals(expected,testSchool.getSchoolPhoneNumber());
    }

    @Test
    void getSchoolWebsite() {
        String expected = "http://school.com";
        assertEquals(expected, testSchool.getSchoolWebsite());
    }

    @Test
    void getSchoolType() {
        String expected = "Public";
        assertEquals(expected, testSchool.getSchoolType());
    }

    @Test
    void testEquals() {

        List<String> expectedGradeLevels = new ArrayList<>();
        expectedGradeLevels.add("Elementary");

        School expected = new School(1234,
                "Fake School",
                new Address("123","Fake Street"),
                new Coordinates(123.123,456.456),
                expectedGradeLevels,
                true,
                true,
                "email@school.com",
                new PhoneNumber("1234567890"),
                "http://school.com",
                "Public"
        );
        assertEquals(expected,testSchool);
    }

    @Test
    void testHashCode() {

        List<String> expectedGradeLevels = new ArrayList<>();
        expectedGradeLevels.add("Elementary");

        School expected = new School(1234,
                "Fake School",
                new Address("123","Fake Street"),
                new Coordinates(123.123,456.456),
                expectedGradeLevels,
                true,
                true,
                "email@school.com",
                new PhoneNumber("1234567890"),
                "http://school.com",
                "Public"
        );
        assertEquals(expected.hashCode(),testSchool.hashCode());


    }


    @Test
    void testToString() {
        String expected =
                "School Name: Fake School\n" +
                        "School Number: 1234\n" +
                        "School Type: Public\n" +
                        "School Address: 123 Fake Street\n" +
                        "School Phone Number: (123) 456-7890\n" +
                        "School Email: email@school.com\n" +
                        "School Website: http://school.com\n" +
                        "School Grade Levels: [Elementary]\n" +
                        "French Immersion: true\n" +
                        "Spanish Bilingual: true\n" +
                        "School Coordinates: (123.123, 456.456)\n";

        assertEquals(expected,testSchool.toString());

    }
}