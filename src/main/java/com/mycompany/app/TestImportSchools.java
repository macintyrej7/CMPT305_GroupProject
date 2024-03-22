package com.mycompany.app;

import com.mycompany.app.schools.School;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestImportSchools {

    public static void main(String[] args) {

        List<School> testList = new ArrayList<>();

        try {
            testList = ImportSchools.readCSV("Edmonton_Schools_Merged - Mar_19_2024.csv");
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
