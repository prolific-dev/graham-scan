package com.prolificdev.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PreCalculation {
    private static final Point ZERO_POINT = new Point(0, 0);

    private final Point startingPoint;
    private final Map<Point, Double> pointAngleMap;

    public PreCalculation(File file) throws IOException {
        List<Point> dataList = new ArrayList<>(new Converter(file).getConvertedDataSet());
        this.startingPoint = setStartingPointByDistanceToZeroPoint(dataList);
        this.pointAngleMap = calcAngleToStartingPointMap(dataList);
    }

    private Map<Point, Double> calcAngleToStartingPointMap(List<Point> dataList) {
        Map<Point, Double> tmpPointAngleToStartingPointMap = new HashMap<>();

        tmpPointAngleToStartingPointMap.put(this.startingPoint, 0.0);
        dataList.remove(this.startingPoint);
        dataList.forEach(point -> tmpPointAngleToStartingPointMap.put(point, point.angle(this.startingPoint)));
        return tmpPointAngleToStartingPointMap;
    }

    private Point setStartingPointByDistanceToZeroPoint(List<Point> dataList) {
        Point tmpStartingPoint = dataList.get(0);
        double tmpDistance = tmpStartingPoint.distance(ZERO_POINT);

        for (Point point : dataList) {
            if (point.distance(ZERO_POINT) < tmpDistance) {
                tmpDistance = point.distance(ZERO_POINT);
                tmpStartingPoint = point;
                continue;
            }
            if (point.distance(ZERO_POINT) == tmpDistance)
                tmpStartingPoint = setStartingPointByXYAxis(tmpStartingPoint, point);
        }
        return tmpStartingPoint;
    }

    private Point setStartingPointByXYAxis(Point tmpStartingPoint, Point point) {
        if (point.getX() < tmpStartingPoint.getX() || point.getY() < tmpStartingPoint.getY())
            return point;
        return tmpStartingPoint;

    }

    public Point getStartingPoint() {
        return this.startingPoint;
    }

    public Map<Point, Double> getPointAngleMap() {
        return this.pointAngleMap;
    }
}
