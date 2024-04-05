package com.mycompany.app;

import com.mycompany.app.properties.Coordinates;
import com.mycompany.app.properties.Neighbourhood;
import com.mycompany.app.residential.AssessmentClass;
import com.mycompany.app.residential.Residence;
import com.mycompany.app.schools.GradeList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class XYDataImporterTest {

    List<Residence> residences;
    @BeforeEach
    void setUp() {
        List<AssessmentClass> assessmentClassList = new ArrayList<>();
        assessmentClassList.add(new AssessmentClass("Residential",100));

        Residence testResidence1 = new Residence(1234,
                assessmentClassList,10000, new Neighbourhood("Neighbourhood","Ward"),
                new Coordinates(53.5707284,-113.3907046));

        Residence testResidence2 = new Residence(1234,
                assessmentClassList,20000, new Neighbourhood("Neighbourhood","Ward"),
                new Coordinates(53.5707284,-113.3907046));

        residences = new ArrayList<>();

        residences.add(testResidence1);
        residences.add(testResidence2);

    }
    @Test
    void minAndMax() {

        XYDataImporter testXYDataImporter = new XYDataImporter(residences);
        List<Long> result = testXYDataImporter.minAndMax();

        List<Long> expected = Arrays.asList(10000L, 20000L);

        assertEquals(expected, result);

    }

    @Test
    void incrementContainers() {
        XYDataImporter testXYDataImporter = new XYDataImporter(residences);
        testXYDataImporter.incrementContainers(4);

        HashMap<Long, Long> expected = new HashMap<>();
        expected.put(10000L, 0L);
        expected.put(2500L, 0L);
        expected.put(5000L, 0L);
        expected.put(7500L, 0L);

        assertEquals(expected, testXYDataImporter.getContainer());
    }

    @Test
    void updateContainers() {
        List<AssessmentClass> assessmentClassList = new ArrayList<>();
        assessmentClassList.add(new AssessmentClass("Residential",100));

        Residence testResidence1 = new Residence(1234,
                assessmentClassList,10000, new Neighbourhood("Neighbourhood","Ward"),
                new Coordinates(53.5707284,-113.3907046));

        Residence testResidence2 = new Residence(1234,
                assessmentClassList,20000, new Neighbourhood("Neighbourhood","Ward"),
                new Coordinates(53.5707284,-113.3907046));

        List<Residence> expected = Arrays.asList(testResidence1,testResidence2);

        XYDataImporter testXYDataImporter = new XYDataImporter(residences);
        testXYDataImporter.incrementContainers(1);
        testXYDataImporter.updateContainers(1);

        assertEquals(expected,testXYDataImporter.getResidenceRawList());
    }

    @Test
    void getResidenceRawList() {
        List<AssessmentClass> assessmentClassList = new ArrayList<>();
        assessmentClassList.add(new AssessmentClass("Residential",100));

        Residence testResidence1 = new Residence(1234,
                assessmentClassList,10000, new Neighbourhood("Neighbourhood","Ward"),
                new Coordinates(53.5707284,-113.3907046));

        Residence testResidence2 = new Residence(1234,
                assessmentClassList,20000, new Neighbourhood("Neighbourhood","Ward"),
                new Coordinates(53.5707284,-113.3907046));

        List<Residence> expected = Arrays.asList(testResidence1,testResidence2);

        XYDataImporter testXYDataImporter = new XYDataImporter(residences);
        testXYDataImporter.incrementContainers(1);
        testXYDataImporter.updateContainers(1);

        assertEquals(expected,testXYDataImporter.getResidenceRawList());
    }

    @Test
    void getContainer() {
        XYDataImporter testXYDataImporter = new XYDataImporter(residences);
        testXYDataImporter.incrementContainers(1);

        HashMap<Long, Long> expected = new HashMap<>();
        expected.put(10000L, 0L);


        assertEquals(expected, testXYDataImporter.getContainer());
    }


}