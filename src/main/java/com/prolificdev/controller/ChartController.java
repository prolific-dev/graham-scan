package com.prolificdev.controller;

import com.prolificdev.model.GrahamScan;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.io.File;
import java.io.IOException;

public class ChartController {
    private final XYChart.Series<Number, Number> innerSeries = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> convexHullSeries = new XYChart.Series<>();
    private File file;

    @FXML
    private LineChart<Number, Number> lineChart;

    @FXML
    public void initializeChart(ActionEvent event) throws IOException {
        this.innerSeries.setName("Inner-Points");
        this.convexHullSeries.setName("Convex-Hull");

        if (file == null) {
            this.file = new File("src/test/resources/com/prolificdev/data/grahamscan_test_data.txt");
            GrahamScan grahamScan = new GrahamScan(file);

            grahamScan.getInnerPoints()
                    .forEach(p -> this.innerSeries.getData().add(new XYChart.Data<>(p.getX(), p.getY())));
            grahamScan.getConvexHull()
                    .forEach(p -> this.convexHullSeries.getData().add(new XYChart.Data<>(p.getX(), p.getY())));

            lineChart.getData().add(this.innerSeries);
            lineChart.getData().add(this.convexHullSeries);
            lineChart.setLegendVisible(true);
        }
    }
}
