/**
 * Authors: Legan Hunter-Mutima, Brian Lin, Jason MacIntyre, Sankalp Shrivastav
 * Course: CMPT 305 AS01
 * Instructor: Dr. Indratmo
 * Assignment: Group project
 * Due date: ???
 * Last worked on: Mar 21, 2024
 * Program name: ImportSchool.java
 * Program description: this program parses a passed .csv file for Edmonton school data, and converts each row in the
 * .csv file into a School object.
 */

package com.mycompany.app;
import com.mycompany.app.properties.Address;
import com.mycompany.app.properties.Coordinates;
import com.mycompany.app.schools.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImportSchools {

    public static List<School> readCSV(String fileName) throws IOException {
        // this function returns a List<School> object, containing School objects whose data correspond to the data in
        // the provided CSV file. This function uses the schoolHelper() helper function to parse each line in the CSV.

        List<School> schools = new ArrayList<>();

        BufferedReader reader = Files.newBufferedReader(Paths.get(fileName));

        reader.readLine(); // read first line to skip the header row.

        String currentLine;

        // keep looping until EOF.
        while ((currentLine = reader.readLine()) != null){

            String[] values = currentLine.split(","); // split on commas.

            School currentSchool = schoolHelper(values); // parse current line.

            schools.add(currentSchool); // add to List.
        }

        return schools;
    }

    private static School schoolHelper(String[] values) {
        // this function returns a School object based on the list of Strings it is passed.

        int schoolNumber;

        // check if the school number is empty (Administration building does not have a school number).
        if (!values[0].isEmpty()){
            schoolNumber = Integer.parseInt(values[0]);
        }
        else {
            schoolNumber = -1;
        }

        String schoolName = values[1];

        String schoolStreet = values[2];

        String schoolPostalCode = values[3];

        Address schoolAddress = new Address(schoolStreet, schoolPostalCode); // build Address object.

        PhoneNumber schoolPhoneNumber = new PhoneNumber(values[4]); // build PhoneNumber object.

        String schoolEmail = values[5];

        String schoolWebsite = values[6];

        List<String> schoolGradeLevels = new ArrayList<>();

        // there will always be at least one grade level.
        schoolGradeLevels.add(values[7]);

        // check if next two columns are empty - if not, add to list of grade levels.
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

        Coordinates schoolCoordinates = new Coordinates(schoolLatitude, schoolLongitude); // build Coordinates object.

        String schoolType = values[14];

        // return a new School object with parsed information.
        return new School(schoolNumber, schoolName, schoolAddress, schoolCoordinates, schoolGradeLevels,
                schoolSpanishBilingual, schoolFrenchImmersion, schoolEmail, schoolPhoneNumber, schoolWebsite,
                schoolType);
    }

    public static List<String> stringToList(String input) {
        // Split the input string by semicolons and trim whitespace
        String[] items = input.split("\\s*;\\s*");
        // Convert the array to a list and return it
        return Arrays.asList(items);
    }
}
