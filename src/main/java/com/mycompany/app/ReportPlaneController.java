package com.mycompany.app;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class ReportPlaneController {

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private StackPane myStackPlane;

    public void initialize() {
        // Create axes
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        // Set labels for axes
        xAxis.setLabel("Category");
        yAxis.setLabel("Value");
//
//        // Create series and add data
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("A", 10));
        series.getData().add(new XYChart.Data<>("B", 20));
        series.getData().add(new XYChart.Data<>("C", 30));

        barChart.setLegendVisible(false);

        // Add series to the chart
        barChart.getData().add(series);
    }

}
