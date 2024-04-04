package com.mycompany.app;

import com.mycompany.app.properties.Coordinates;
import com.mycompany.app.residential.Residence;
import com.mycompany.app.schools.School;
import com.mycompany.app.utilities.AssessmentValueStatistics;
import com.mycompany.app.utilities.Calculations;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ReportPlaneController {

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private StackPane myStackPlane;

    @FXML
    private BarChart<Number, String> highestChart;

    @FXML BarChart<Number, String> lowestChart;

    private List<School> myschools;
    private List<Residence> myResidences;
    private XYDataImporter xyDataImporter;
    private long MIN_VALUE = 40000;
    private long MAX_VALUE = 1000000;

    public void initialize() throws IOException {

        // Create axes
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        myResidences = ImportResidences.readCSVResidentialBetweenValues("Property_Assessment_Data_2024.csv", MIN_VALUE, MAX_VALUE);
        xyDataImporter = new XYDataImporter(myResidences);
        xyDataImporter.incrementContainers(10);
        xyDataImporter.updateContainers(10);

        Map<Long, Long> incrementContainer = xyDataImporter.container;

        // Create series and add data
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        // get from class and add to the data

        for (long e: incrementContainer.keySet().stream().sorted().toList()){
            long yVal = incrementContainer.get(e).intValue();
            series.getData().add(new XYChart.Data<>(Long.toString(e), yVal));
        }

        barChart.setLegendVisible(false);

        // Add series to the chart
        barChart.getData().add(series);

        myschools = ImportSchools.readCSV("merged_file.csv");

        Map<School, AssessmentValueStatistics> schoolValuesMap = mapSchoolValues(myschools, myResidences);

        List<Map.Entry<School, AssessmentValueStatistics>> sortedSchoolValuesMap = schoolValuesMap.entrySet().stream()
                .collect(Collectors.toList())
                .stream()
                .sorted((entry1, entry2) -> (Double.valueOf(entry1.getValue().getAverage()).compareTo(Double.valueOf(entry2.getValue().getAverage()))))
                .collect(Collectors.toList());

        NumberAxis highestX = new NumberAxis();
        CategoryAxis highestY = new CategoryAxis();

        XYChart.Series<Number, String> highestMeanSeries = new XYChart.Series<>();
        highestMeanSeries.setName("Mean");

        XYChart.Series<Number, String> lowestMeanSeries = new XYChart.Series<>();
        lowestMeanSeries.setName("Mean");

        XYChart.Series<Number, String> highestMedianSeries = new XYChart.Series<>();
        highestMedianSeries.setName("Median");

        XYChart.Series<Number, String> lowestMedianSeries = new XYChart.Series<>();
        lowestMedianSeries.setName("Median");

        for (int i = 0; i < 10; i++){

            Map.Entry highestEntry = sortedSchoolValuesMap.get(sortedSchoolValuesMap.size()-(10-i));

            School highestSchool = (School) highestEntry.getKey();

            AssessmentValueStatistics highestSchoolValues = (AssessmentValueStatistics) highestEntry.getValue();

            highestMeanSeries.getData().add(new XYChart.Data<Number, String>(highestSchoolValues.getAverage(), highestSchool.getSchoolName()));
            highestMedianSeries.getData().add(new XYChart.Data<Number, String>(highestSchoolValues.getMedian(), highestSchool.getSchoolName()));

            Map.Entry lowestEntry = sortedSchoolValuesMap.get(i);

            School lowestSchool = (School) lowestEntry.getKey();

            AssessmentValueStatistics lowestSchoolValues = (AssessmentValueStatistics) lowestEntry.getValue();

            lowestMeanSeries.getData().add(new XYChart.Data<Number, String>(lowestSchoolValues.getAverage(), lowestSchool.getSchoolName()));
            lowestMedianSeries.getData().add(new XYChart.Data<Number, String>(lowestSchoolValues.getMedian(), lowestSchool.getSchoolName()));
        }

        highestChart.getData().add(highestMeanSeries);
        highestChart.getData().add(highestMedianSeries);

        lowestChart.getData().add(lowestMeanSeries);
        lowestChart.getData().add(lowestMedianSeries);
    }

    private Map<School, AssessmentValueStatistics> mapSchoolValues(List<School> schools, List<Residence> residences){

        Map<School, AssessmentValueStatistics> schoolsMap = new HashMap<>();

        for (School school : schools){

            AssessmentValueStatistics schoolStatistics = Calculations.calculateAssessmentValueStatistics(residences, 2.0, school.getCoordinates());

            schoolsMap.put(school, schoolStatistics);
        }

        return schoolsMap;
    }

}
