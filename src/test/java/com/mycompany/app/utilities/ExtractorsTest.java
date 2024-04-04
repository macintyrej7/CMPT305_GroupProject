package com.mycompany.app.utilities;

import com.mycompany.app.properties.Address;
import com.mycompany.app.properties.Coordinates;
import com.mycompany.app.schools.GradeList;
import com.mycompany.app.schools.PhoneNumber;
import com.mycompany.app.schools.School;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExtractorsTest {
    List<School> testSchoolList;

    @BeforeEach
    void setUp() {
        // Initialize the instance variable
        testSchoolList = new ArrayList<>();

        List<String> gradeLevels = new ArrayList<>();
        gradeLevels.add("Elementary");

        List<String> gradeList = new ArrayList<>();
        gradeList.add("K");
        gradeList.add("1");
        gradeList.add("2");
        gradeList.add("3");
        gradeList.add("4");
        gradeList.add("5");
        gradeList.add("6");

        List<String> languageList = new ArrayList<>();
        languageList.add("Italian Language and Culture");
        languageList.add("Spanish Language and Culture");

        School testSchool = new School(1234,
                "Fake School",
                new Address("123", "Fake Street"),
                new Coordinates(123.123, 456.456),
                gradeLevels,
                true,
                true,
                "email@school.com",
                new PhoneNumber("1234567890"),
                "http://school.com",
                "Public",
                new GradeList(gradeList),
                languageList
        );

        testSchoolList.add(testSchool);
        testSchoolList.add(testSchool);
        testSchoolList.add(testSchool);
    }

    @Test
    void uniqueSortedListItems() {
        List<String> expected = new ArrayList<>();
        expected.add("Italian Language and Culture");
        expected.add("Spanish Language and Culture");

        assertEquals(expected, Extractors.uniqueListValues(testSchoolList, School::getSchoolLanguageList));

        List<String> expected2 = new ArrayList<>();
        expected2.add("K");
        expected2.add("1");
        expected2.add("2");
        expected2.add("3");
        expected2.add("4");
        expected2.add("5");
        expected2.add("6");

        assertEquals(expected2.toString(), Extractors.uniqueListValues(testSchoolList, School::getSchoolGradeList).toString());
    }
}