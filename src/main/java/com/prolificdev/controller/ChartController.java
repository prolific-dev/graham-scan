package com.prolificdev.controller;

import com.prolificdev.model.GrahamScan;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.io.File;
import java.io.IOException;

public class ChartController {
    private File file;
    private final XYChart.Series<Number, Number> innerSeries = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> convexHullSeries = new XYChart.Series<>();

    @FXML
    private LineChart<Number, Number> lineChart;


    @FXML
    public void initializeChart(ActionEvent event) throws IOException {
        if (file == null) {
            this.file = new File("src/test/resources/com/prolificdev/data/grahamscan_test_data.txt");
            GrahamScan grahamScan = new GrahamScan(file);

            this.innerSeries.setName("Inner-Points");
            this.convexHullSeries.setName("Convex-Hull");

            grahamScan.getInnerPoints()
                    .forEach(p -> this.innerSeries.getData().add(new XYChart.Data<>(p.getX(), p.getY())));
            grahamScan.getConvexHull()
                    .forEach(p -> this.convexHullSeries.getData().add(new XYChart.Data<>(p.getX(), p.getY())));

            lineChart.getData().add(this.innerSeries);
            lineChart.getData().add(this.convexHullSeries);
            lineChart.setLegendVisible(true);
        }
    }

    public File getFile() {
        return this.file;
    }

    public XYChart.Series<Number, Number> getInnerSeries() {
        return this.innerSeries;
    }

    public XYChart.Series<Number, Number> getConvexHullSeries() {
        return this.convexHullSeries;
    }

    public LineChart<Number, Number> getLineChart() {
        return this.lineChart;
    }
}
