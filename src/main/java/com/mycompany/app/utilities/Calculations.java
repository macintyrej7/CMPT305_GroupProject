package com.mycompany.app.utilities;

import com.mycompany.app.properties.Coordinates;
import com.mycompany.app.residential.Residence;

import java.util.List;
import java.util.OptionalDouble;

public class Calculations {

    public static double CalculateDistance(Coordinates coordinate1, Coordinates coordinate2) {
        double latitudeRadians1 = Math.toRadians(coordinate1.getLatitude());
        double latitudeRadians2 = Math.toRadians(coordinate2.getLatitude());
        double longitudeRadians1 = Math.toRadians(coordinate1.getLongitude());
        double longitudeRadians2 = Math.toRadians(coordinate2.getLongitude());

        double x = (longitudeRadians2 - longitudeRadians1) * Math.cos((latitudeRadians1 + latitudeRadians2) / 2);
        double y = (latitudeRadians2 - latitudeRadians1);
        //Earth Radius is 6371 Radians
        double distance = Math.sqrt(x * x + y * y) * 6371;

        return distance;
    }

    public static Double CalculateAverageAssessmentValue(List<Residence> residences, Double distance, Coordinates coordinates) {
        OptionalDouble average = residences.stream()
                .filter(t -> CalculateDistance(t.getCoordinates(), coordinates) < distance)
                .mapToDouble(t -> t.getAssessedValue())
                .average();

        return average.isPresent() ? average.getAsDouble() : 0.0;
    }
}
