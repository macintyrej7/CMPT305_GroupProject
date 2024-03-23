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

    public static List<Residence> readCSV(String fileName) throws IOException {

        List<Residence> residences = new ArrayList<>();

        BufferedReader reader = Files.newBufferedReader(Paths.get(fileName));

        reader.readLine();

        String currentLine;

        while ((currentLine = reader.readLine()) != null){

            String[] values = currentLine.split(",");

            Residence currentResidence = residenceHelper(values);

            residences.add(currentResidence);
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

