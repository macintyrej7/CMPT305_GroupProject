package com.mycompany.app;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ImportSchoolsTest {



    @Test
    void stringToList() {
        ImportSchools importSchools = new ImportSchools();

        String input1 = "Hockey Academy; Recreation Academy; Soccer Academy";
        List<String> expected1 = Arrays.asList("Hockey Academy", "Recreation Academy", "Soccer Academy");
        assertEquals(expected1, importSchools.stringToList(input1));

        String input2 = "Italian Language and Culture; Spanish Language and Culture";
        List<String> expected2 = Arrays.asList("Italian Language and Culture", "Spanish Language and Culture");
        assertEquals(expected2, importSchools.stringToList(input2));

        String input3 = "Ukrainian Bilingual";
        List<String> expected3 = Arrays.asList("Ukrainian Bilingual");
        assertEquals(expected3, importSchools.stringToList(input3));

        String input4 = "Soccer Academy";
        List<String> expected4 = Arrays.asList("Soccer Academy");
        assertEquals(expected4, importSchools.stringToList(input4));

        String input5 = "K; 1; 2; 3; 4; 5; 6";
        List<String> expected5 = Arrays.asList("K", "1", "2", "3", "4", "5", "6");
        assertEquals(expected5, importSchools.stringToList(input5));

    }
}