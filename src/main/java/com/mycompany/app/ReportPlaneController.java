package com.mycompany.app;

import com.mycompany.app.residential.Residence;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.List;

public class ReportPlaneController {

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

        myResidences = ImportResidences.readCSV("Property_Assessment_Data_2024.csv");
        xyDataImporter = new XYDataImporter(myResidences);
        xyDataImporter.minAndMax();

//
//        // Create series and add data
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        // get from class and add to the data

        series.getData().add(new XYChart.Data<>("A", 10));
        series.getData().add(new XYChart.Data<>("B", 20));
        series.getData().add(new XYChart.Data<>("C", 30));

        barChart.setLegendVisible(false);

        // Add series to the chart
        barChart.getData().add(series);
    }

}
