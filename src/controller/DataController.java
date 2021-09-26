package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import model.Converter;
import model.GeomCalc;
import model.GrahamScan;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataController {
    @FXML
    private LineChart<Number, Number> lineChart;

    @FXML
    private Button button;

    public void initialize(ActionEvent e) throws IOException {
        Converter converter = new Converter(new File("src/data/data.txt"));
        GeomCalc geomCalc = new GeomCalc(converter.getInput());
        GrahamScan grahamScan = new GrahamScan(geomCalc.getAngleDataMap());

        NumberAxis xAxis = new NumberAxis("x-Achse", 0.0, 10.0, 1);
        NumberAxis yAxis = new NumberAxis("y-Achse", 0.0, 10.0, 1);

        lineChart = new LineChart<>(xAxis, yAxis);

        XYChart.Series<Number, Number> inner = new XYChart.Series<>();
        XYChart.Series<Number, Number> outer = new XYChart.Series<>();

        inner.setName("Inner");
        outer.setName("Hull");

        List<Point2D> innerList = new ArrayList<>();
        List<Point2D> outerList = new ArrayList<>();

        for (Map.Entry<Point2D, Double> entry : grahamScan.getInner().entrySet()) {
            innerList.add(entry.getKey());
        }

        for (Map.Entry<Point2D, Double> entry : grahamScan.getOuter()) {
            outerList.add(entry.getKey());
        }

        for (Point2D p : innerList) {
            inner.getData().add(new XYChart.Data<>(p.getX(), p.getY()));
        }

        for (Point2D p : outerList) {
            outer.getData().add(new XYChart.Data<>(p.getX(), p.getY()));
        }

        lineChart.getData().add(inner);
        lineChart.getData().add(outer);

        lineChart.setAnimated(false);
        lineChart.setCreateSymbols(true);
        lineChart.setAxisSortingPolicy(LineChart.SortingPolicy.NONE);
        lineChart.setLegendVisible(true);

        System.out.println("initialize");
    }
}
