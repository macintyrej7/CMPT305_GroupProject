package com.mycompany.app;

import com.mycompany.app.residential.AssessmentClass;
import com.mycompany.app.properties.Coordinates;
import com.mycompany.app.residential.Residence;
import com.mycompany.app.schools.School;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ImportResidencesTest {
    @Test
    void readCSV() throws IOException {
        ImportResidences importResidences = new ImportResidences();
        List<Residence> residences = importResidences.readCSV("Data/residenceTest.csv", 500000);
        assertEquals(1, residences.size());
        Residence residence = residences.get(0);
        assertEquals(1234, residence.getAccountNumber());
        assertEquals(new Coordinates(123.123, 456.456), residence.getCoordinates());
    }

    @Test
    void assessmentClassListHelper() {
        ImportResidences importResidences = new ImportResidences();
        String[] rowData = {"", "", "", "", "", "", "", "", "", "", "", "", "100", "", "", "Residential", "","",""};
        List<AssessmentClass> assessmentClassList = importResidences.assessmentClassListHelper(rowData, 12, 14);

        assertEquals(1, assessmentClassList.size());
        assertEquals(new AssessmentClass("Residential", 100), assessmentClassList.get(0));

    }

    public static class TestImportSchools {

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
}