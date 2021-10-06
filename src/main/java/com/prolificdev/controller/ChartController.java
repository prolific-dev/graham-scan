package com.prolificdev.controller;

import com.prolificdev.model.GrahamScan;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.io.File;
import java.io.IOException;

public class ChartController {
    @FXML
    private LineChart<Number, Number> lineChart;

    @FXML
    public void initializeChart(ActionEvent event) throws IOException {
        File file = new File("src/test/resources/com/prolificdev/data/grahamscan_test_data.txt");
        GrahamScan grahamScan = new GrahamScan(file);

        XYChart.Series<Number, Number> innerSeries = new XYChart.Series<>();
        XYChart.Series<Number, Number> convexHullSeries = new XYChart.Series<>();

        innerSeries.setName("Inner-Points");
        convexHullSeries.setName("Convex-Hull");

        grahamScan.getInnerPoints().forEach(p -> innerSeries.getData().add(new XYChart.Data<>(p.getX(), p.getY())));
        grahamScan.getConvexHull().forEach(p -> convexHullSeries.getData().add(new XYChart.Data<>(p.getX(), p.getY())));

        lineChart.getData().add(innerSeries);
        lineChart.getData().add(convexHullSeries);
        lineChart.setLegendVisible(true);
        lineChart.setCreateSymbols(true);
    }
}
