package com.mycompany.app;
import com.mycompany.app.schools.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ImportSchools {

    public static List<School> readCSV(String fileName) throws IOException {

        List<School> schools = new ArrayList<>();

        BufferedReader reader = Files.newBufferedReader(Paths.get(fileName));

        reader.readLine();

        String currentLine;

        while ((currentLine = reader.readLine()) != null){

            String[] values = currentLine.split(",");

            School currentSchool = schoolHelper(values);

            schools.add(currentSchool);
        }

        return schools;
    }

    private static School schoolHelper(String[] values) {

        int schoolNumber;

        if (!values[0].isEmpty()){
            schoolNumber = Integer.parseInt(values[0]);
        }
        else {
            schoolNumber = -1;
        }

        String schoolName = values[1];

        String schoolStreet = values[2];

        String schoolPostalCode = values[3];

        Address schoolAddress = new Address(schoolStreet, schoolPostalCode);

        PhoneNumber schoolPhoneNumber = new PhoneNumber(values[4]);

        String schoolEmail = values[5];

        String schoolWebsite = values[6];

        List<String> schoolGradeLevels = new ArrayList<>();

        schoolGradeLevels.add(values[7]);

        if (!values[8].isEmpty()){
            schoolGradeLevels.add(values[8]);
        }

        if (!values[9].isEmpty()){
            schoolGradeLevels.add(values[9]);
        }

        boolean schoolFrenchImmersion = Boolean.parseBoolean(values[10]);

        boolean schoolSpanishBilingual = Boolean.parseBoolean(values[11]);

        double schoolLatitude = Double.parseDouble(values[12]);

        double schoolLongitude = Double.parseDouble(values[13]);

        Coordinates schoolCoordinates = new Coordinates(schoolLatitude, schoolLongitude);

        String schoolType = values[14];

        return new School(schoolNumber, schoolName, schoolAddress, schoolCoordinates, schoolGradeLevels,
                schoolSpanishBilingual, schoolFrenchImmersion, schoolEmail, schoolPhoneNumber, schoolWebsite,
                schoolType);
    }
}
