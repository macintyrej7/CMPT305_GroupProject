package com.mycompany.app;

import com.mycompany.app.residential.Residence;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportPlaneController {

    private long MAX_VALUE = 500000;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private StackPane myStackPlane;

    private List<Residence> myResidences;
    private XYDataImporter xyDataImporter;

    public void initialize() throws IOException {
        // Create axes
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        // Set labels for axes
        xAxis.setLabel("Category");
        yAxis.setLabel("Value");

        myResidences = ImportResidences.readCSV("Property_Assessment_Data_2024.csv", MAX_VALUE);
        xyDataImporter = new XYDataImporter(myResidences);
        xyDataImporter.incrementContainers(10);
        xyDataImporter.updateContainers(10);

        Map<Long, Long> incrementContainer = xyDataImporter.container;

//
//        // Create series and add data
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        // get from class and add to the data

        for (long e: incrementContainer.keySet().stream().sorted().toList()){
            long yVal = incrementContainer.get(e).intValue();
            series.getData().add(new XYChart.Data<>(Long.toString(e), yVal));
        }

        barChart.setLegendVisible(false);

        // Add series to the chart
        barChart.getData().add(series);
    }

}
