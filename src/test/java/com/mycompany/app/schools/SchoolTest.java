package com.mycompany.app.schools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SchoolTest {
    School testSchool;
    @BeforeEach
    void setUp() {

        testSchool = new School(1234,
                "Fake School",
                "Public",
                new Address("123","Fake Street"),
                new Coordinates(123.123,456.456),
                "Elementary",
                true,
                true,
                "email@school.com",
                new PhoneNumber("1234567890"),
                "http://school.com"
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
    void getSchoolType() {
        String expected = "Public";
        assertEquals(expected,testSchool.getSchoolType());
    }

    @Test
    void getSchoolAddress() {
        Address expected = new Address("123","Fake Street");
        assertEquals(expected,testSchool.getSchoolAddress());

    }

    @Test
    void getSchoolCoordinates() {
        Coordinates expected = new Coordinates(123.123,456.456);
        assertEquals(expected,testSchool.getSchoolCoordinates());
    }

    @Test
    void getSchooLGradeLevel() {
        String expected = "Elementary";
        assertEquals(expected,testSchool.getSchooLGradeLevel());
    }

    @Test
    void isHasSpanish() {
        boolean expected = true;
        assertEquals(expected,testSchool.isHasSpanish());
    }

    @Test
    void isHasFrench() {
        boolean expected = true;
        assertEquals(expected,testSchool.isHasFrench());
    }

    @Test
    void getEmail() {
        String expected = "email@school.com";
        assertEquals(expected,testSchool.getEmail());

    }

    @Test
    void getSchoolPhoneNumber() {
        PhoneNumber expected = new PhoneNumber("1234567890");
        assertEquals(expected,testSchool.getSchoolPhoneNumber());
    }

    @Test
    void getSchoolWebsite() {
        String expected = "http://school.com";
        assertEquals(expected,testSchool.getSchoolWebsite());
    }

    @Test
    void testEquals() {
        School expected = new School(1234,
                "Fake School",
                "Public",
                new Address("123","Fake Street"),
                new Coordinates(123.123,456.456),
                "Elementary",
                true,
                true,
                "email@school.com",
                new PhoneNumber("1234567890"),
                "http://school.com"
        );
        assertEquals(expected,testSchool);
    }

    @Test
    void testHashCode() {
        School expected = new School(1234,
                "Fake School",
                "Public",
                new Address("123","Fake Street"),
                new Coordinates(123.123,456.456),
                "Elementary",
                true,
                true,
                "email@school.com",
                new PhoneNumber("1234567890"),
                "http://school.com"
        );
        assertEquals(expected.hashCode(),testSchool.hashCode());
    }
}