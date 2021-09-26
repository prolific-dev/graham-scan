package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import model.Converter;
import model.GeomCalc;
import model.GrahamScan;

import java.io.File;
import java.io.IOException;
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

        XYChart.Series<Number, Number> inner = new XYChart.Series<>();
        XYChart.Series<Number, Number> outer = new XYChart.Series<>();

        for (Map.Entry<Point2D, Double> entry : grahamScan.getOuter()) {
            outer.getData().add(new XYChart.Data<>(entry.getKey().getX(), entry.getKey().getY()));
        }

        for (Map.Entry<Point2D, Double> entry : grahamScan.getInner().entrySet()) {
            inner.getData().add(new XYChart.Data<>(entry.getKey().getX(), entry.getKey().getY()));
        }

        inner.setName("Inner");
        outer.setName("Hull");

        lineChart.getData().add(inner);
        lineChart.getData().add(outer);

        lineChart.setAnimated(false);
        lineChart.setCreateSymbols(true);
        lineChart.setAxisSortingPolicy(LineChart.SortingPolicy.NONE);
        lineChart.setLegendVisible(true);

        System.out.println("initialize");
    }
}
