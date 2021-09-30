package com.prolificdev.controllers;

import java.io.File;
import java.io.IOException;

import com.prolificdev.App;
import com.prolificdev.models.MyData;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

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
        MyData myData = new MyData(new File("src/main/resources/com/prolificdev/data/data.txt"));
        XYChart.Series<Number, Number> seriesInner = new XYChart.Series<>();
        XYChart.Series<Number, Number> seriesHull = new XYChart.Series<>();

        if (seriesInner.getData().isEmpty()) {
            seriesInner.setName("inner");
            seriesHull.setName("hull");

            for (Point2D p : myData.getInnerList()) {
                seriesInner.getData().add(new XYChart.Data<>(p.getX(), p.getY()));
            }

            for (Point2D p : myData.getHullList()) {
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