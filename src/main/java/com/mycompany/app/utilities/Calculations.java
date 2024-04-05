package com.mycompany.app.utilities;

import com.mycompany.app.properties.Coordinates;
import com.mycompany.app.residential.Residence;

import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

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

    public static double calculateMedian(List<Double> doublesList){
        int length = doublesList.size();

        if (length == 0) {
            return 0.0;
        }

        Collections.sort(doublesList);

        if (length % 2 == 0) {
            return (doublesList.get(length / 2 - 1) + doublesList.get(length / 2)) / 2.0;
        } else {
            return doublesList.get(length /2);
        }
    }

    public static AssessmentValueStatistics calculateAssessmentValueStatistics(
            List<Residence> residences, Double distance, Coordinates coordinates){

        List<Double> filteredValues = residences.stream()
                .filter(t -> CalculateDistance(t.getCoordinates(), coordinates) < distance)
                .map(t -> (double)t.getAssessedValue())
                .collect(Collectors.toList());

        DoubleSummaryStatistics statistics = filteredValues.stream()
                .mapToDouble(Double::doubleValue)
                .summaryStatistics();

        double medianValue = calculateMedian(filteredValues);

        return new AssessmentValueStatistics(statistics.getAverage(),statistics.getMax(),statistics.getMin(),
                medianValue,filteredValues.size());

    }


}
