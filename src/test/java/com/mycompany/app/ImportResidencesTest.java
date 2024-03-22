package com.mycompany.app;

import com.mycompany.app.residential.AssessmentClass;
import com.mycompany.app.properties.Coordinates;
import com.mycompany.app.residential.Residence;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ImportResidencesTest {
    @Test
    void readCSV() throws IOException {
        ImportResidences importResidences = new ImportResidences();
        List<Residence> residences = importResidences.readCSV("residenceTest.csv");
        // Assuming the CSV file contains at least one valid entry
        assertEquals(1, residences.size());
        Residence residence = residences.get(0);
        assertEquals(1234, residence.getAccountNumber());
        assertEquals(new Coordinates(123.123, 456.456), residence.getCoordinates());
        // Add more assertions as needed for other fields
    }

    @Test
    void assessmentClassListHelper() {
        ImportResidences importResidences = new ImportResidences();
        String[] rowData = {"", "", "", "", "", "", "", "", "", "", "", "", "100", "", "", "Residential", "","",""};
        List<AssessmentClass> assessmentClassList = importResidences.assessmentClassListHelper(rowData, 12, 14);

        assertEquals(1, assessmentClassList.size());
        // Assuming the values in rowData match the expected AssessmentClass entries
        assertEquals(new AssessmentClass("Residential", 100), assessmentClassList.get(0));

    }
}