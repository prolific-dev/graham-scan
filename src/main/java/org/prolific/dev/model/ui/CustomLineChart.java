package org.prolific.dev.model.ui;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.shape.Line;
import org.prolific.dev.model.math.Point;

import java.util.List;
import java.util.Set;

public class CustomLineChart extends LineChart<Number, Number> {

    private XYChart.Series<Number, Number> convexHullSeries;
    private XYChart.Series<Number, Number> pointsSeries;
    public CustomLineChart() {
        super(new NumberAxis(), new NumberAxis());

        this.convexHullSeries = new XYChart.Series<>();
        this.pointsSeries = new XYChart.Series<>();
    }

    @Override
    protected void layoutPlotChildren() {
        super.layoutPlotChildren();

        getPlotChildren().removeIf(node -> node instanceof Line);

        if (getData().isEmpty())
            return;

        XYChart.Series<Number, Number> series = getData().get(0);
        List<XYChart.Data<Number, Number>> points = series.getData();

        for (int i = 0; i < points.size() - 1; i++) {
            XYChart.Data<Number, Number> p1 = points.get(i);
            XYChart.Data<Number, Number> p2 = points.get(i + 1);

            if (i % 2 == 0) {
                XYChart.Data<Number, Number> tmp = p1;
                p1 = p2;
                p2 = tmp;
            }

            double x1 = getXAxis().getDisplayPosition(p1.getXValue());
            double y1 = getYAxis().getDisplayPosition(p1.getYValue());
            double x2 = getXAxis().getDisplayPosition(p2.getXValue());
            double y2 = getYAxis().getDisplayPosition(p2.getYValue());

            Line customLine = new Line(x1, y1, x2, y2);

            getPlotChildren().add(customLine);
        }

        XYChart.Data<Number, Number> p1 = points.get(points.size() - 1);
        XYChart.Data<Number, Number> p2 = points.get(0);

        double x1 = getXAxis().getDisplayPosition(p1.getXValue());
        double y1 = getYAxis().getDisplayPosition(p1.getYValue());
        double x2 = getXAxis().getDisplayPosition(p2.getXValue());
        double y2 = getYAxis().getDisplayPosition(p2.getYValue());

        Line customLine = new Line(x1, y1, x2, y2);
        getPlotChildren().add(customLine);

        Platform.runLater(() -> hideSeriesLine(this.pointsSeries));
        Platform.runLater(() -> hideSeriesLine(this.convexHullSeries));
    }

    private void hideSeriesLine(XYChart.Series<Number, Number> series) {
        Platform.runLater(() -> {
            Node line = series.getNode().lookup(".chart-series-line");

            if ( line != null)
                line.setStyle("-fx-stroke: transparent");
        });
    }
    public void updateChartData(Set<Point> points, List<Point> convexHull) {

        super.getData().clear();
        this.convexHullSeries = new XYChart.Series<>();
        this.pointsSeries = new XYChart.Series<>();
        this.convexHullSeries.setName("Convex Hull Data");
        this.pointsSeries.setName("Points Data");

        convexHull.forEach(p -> this.convexHullSeries.getData().add(new XYChart.Data<>(p.x, p.y)));

        points.stream()
                .filter(p -> !convexHull.contains(p))
                .forEach(p -> this.pointsSeries.getData().add(new XYChart.Data<>(p.x, p.y)));

        super.getData().add(this.convexHullSeries);
        super.getData().add(this.pointsSeries);
    }
}
