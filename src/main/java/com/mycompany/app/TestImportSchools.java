/**
 * Authors: Legan Hunter-Mutima, Brian Lin, Jason MacIntyre, Sankalp Shrivastav
 * Course: CMPT 305 AS01
 * Instructor: Dr. Indratmo
 * Assignment: Group project
 * Due date: ???
 * Last worked on: Mar 21, 2024
 * Program name: TestImportSchools.java
 * Program description: this program is a simple test that calls the methods from ImportSchools.
 */

package com.mycompany.app;

import com.mycompany.app.schools.School;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestImportSchools {

    public static void main(String[] args) {

        List<School> testList = new ArrayList<>();

        try {
            testList = ImportSchools.readCSV("Edmonton_Schools_Merged - Mar_21_2024.csv");
        }
        catch (IOException ex) {
            System.out.println("Error: " + ex);
            return;
        }

        int count =0;

        for (School school : testList){
            count++;
            System.out.println(count);
            System.out.println(school.toString());
        }
    }
}
