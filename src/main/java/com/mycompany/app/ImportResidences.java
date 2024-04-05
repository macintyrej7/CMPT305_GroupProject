/**
 * Authors: Legan Hunter-Mutima, Brian Lin, Jason MacIntyre, Sankalp Shrivastav
 * Course: CMPT 305 AS01
 * Instructor: Dr. Indratmo
 * Assignment: Group project
 * Program name: 'ImportSchool.java'
 * Program description: this program parses a passed .csv file for Edmonton property data, and converts each row in the
 * .csv file into a Residence object.
 */

package com.mycompany.app;

import com.mycompany.app.properties.Coordinates;
import com.mycompany.app.properties.Neighbourhood;
import com.mycompany.app.residential.AssessmentClass;
import com.mycompany.app.residential.Residence;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ImportResidences {

    public static List<Residence> readCSV(String fileName, long maxValue) throws IOException {

        List<Residence> residences = new ArrayList<>();

        BufferedReader reader = Files.newBufferedReader(Paths.get(fileName));

        reader.readLine();

        String currentLine;

        while ((currentLine = reader.readLine()) != null){

            String[] values = currentLine.split(",");

            Residence currentResidence = residenceHelper(values);

            if (currentResidence.getAssessedValue() > maxValue) continue;

            residences.add(currentResidence);
        }

        return residences;
    }

    public static List<Residence> readCSVResidentialBetweenValues(String fileName, long minValue, long maxValue) throws IOException {

        List<Residence> residences = new ArrayList<>();

        BufferedReader reader = Files.newBufferedReader(Paths.get(fileName));

        reader.readLine();

        String currentLine;

        while ((currentLine = reader.readLine()) != null){

            String[] values = currentLine.split(",");

            Residence currentResidence = residenceHelper(values);

            if (!currentResidence.containsAssessmentClass("RESIDENTIAL"))
                continue; // only consider residential properties

            if (currentResidence.getAssessedValue() <= maxValue && currentResidence.getAssessedValue() >= minValue){
                residences.add(currentResidence); // add residence if it falls into the value range
            }


        }

        return residences;
    }

    private static Residence residenceHelper(String[] values) {

        int propertyAssessmentNumber;

        if (!values[0].isEmpty()){
            propertyAssessmentNumber = Integer.parseInt(values[0]);
        }
        else {
            propertyAssessmentNumber = -1;
        }

        int assessedValue = Integer.parseInt(values[8]);

        Neighbourhood neighbourhood = new Neighbourhood(values[6],values[7]);

        List<AssessmentClass> assessmentClassList;

        assessmentClassList = assessmentClassListHelper(values,12,14);

        Coordinates coordinates = new Coordinates(Double.parseDouble(values[9]),Double.parseDouble(values[10]));

        return new Residence(propertyAssessmentNumber,assessmentClassList,assessedValue,neighbourhood,
                coordinates);
    }

    public static List<AssessmentClass> assessmentClassListHelper(String[] rowData, int startColumn, int endColumn) {
        List<AssessmentClass> assessmentClassList = new ArrayList<>();

        for(int i = startColumn;i<endColumn+1;i++){
            if(rowData[i] != "") {
                assessmentClassList.add(new AssessmentClass(rowData[i + 3], Double.parseDouble(rowData[i])));
            }
        }
        return assessmentClassList;
    }

}

