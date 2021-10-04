package com.prolificdev.controller;

import com.prolificdev.App;
import com.prolificdev.model.GrahamScan;
import com.prolificdev.model.Point;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.io.File;
import java.io.IOException;

public class SecondaryController {
    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private LineChart<Number, Number> lineChart;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void initialize() throws IOException {
        GrahamScan grahamScan = new GrahamScan(new File("src/test/resources/com/prolificdev/data/grahamscan_test_data.txt"));

        XYChart.Series<Number, Number> seriesInner = new XYChart.Series<>();
        XYChart.Series<Number, Number> seriesHull = new XYChart.Series<>();

        if (seriesInner.getData().isEmpty()) {
            seriesInner.setName("inner");
            seriesHull.setName("hull");

            for (Point p : grahamScan.getInnerPoints()) {
                seriesInner.getData().add(new XYChart.Data<>(p.getX(), p.getY()));
            }

            for (Point p : grahamScan.getConvexHull()) {
                seriesHull.getData().add(new XYChart.Data<>(p.getX(), p.getY()));
            }

            lineChart.getData().add(seriesInner);
            lineChart.getData().add(seriesHull);
            lineChart.setLegendVisible(true);
            lineChart.setCreateSymbols(true);

            System.out.println("initialize");
        }
    }
}