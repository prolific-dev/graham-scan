package com.prolificdev.models;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeomCalc {
    private static final Point2D ZERO = new Point2D(0, 0);
    private final Point2D minPoint;
    private final List<Point2D> dataList;
    private final Map<Point2D, Double> angleDataMap;

    public GeomCalc(List<Point2D> input) {
        //this.dataList = normalizeInput(input);
        this.dataList = input;
        this.minPoint = minPointCalc();
        this.angleDataMap = mapAngleData();

    }

    private List<Point2D> normalizeInput(List<Point2D> input) {
        List<Point2D> normalizedList = new ArrayList<>();
        double minX = ZERO.getX();
        double maxX = ZERO.getX();
        double minY = ZERO.getY();
        double maxY = ZERO.getY();

        for (Point2D p : input) {
            if (p.getX() < minX)
                minX = p.getX();
            if (p.getY() < minY)
                minY = p.getY();
            if (p.getX() > maxX)
                maxX = p.getX();
            if (p.getY() > maxY)
                maxY = p.getY();
        }

        for (Point2D p : input) {
            double normX = (p.getX() - minX) / (maxX - minX);
            double normY = (p.getY() - minY) / (maxY - minY);

            normalizedList.add(new Point2D(normX, normY));
        }
        return normalizedList;
    }

    private Point2D minPointCalc() {
        Point2D minPoint = this.dataList.get(0);
        double distance = ZERO.distance(minPoint);

        for (Point2D p : this.dataList) {
            if (ZERO.distance(p) < distance) {
                distance = ZERO.distance(p);
                minPoint = p;
            } else if (ZERO.distance(p) == distance) {
                if (p.getY() < minPoint.getY()) {
                    minPoint = p;
                } else if (p.getX() < minPoint.getX()) {
                    minPoint = p;
                }
            }
        }
        return minPoint;
    }

    private Map<Point2D, Double> mapAngleData() {
        Map<Point2D, Double> map = new HashMap<>();
        List<Point2D> dataList = this.dataList;
        Point2D minPoint = this.minPoint;

        map.put(minPoint, 0.0);
        dataList.remove(minPoint);
        dataList.forEach(p -> map.put(p, getAngleFromMinpoint(minPoint, p)));

        return map;
    }

    private Double getAngleFromMinpoint(Point2D center, Point2D target) {
        // calculate angle theta
        double theta = Math.atan2(target.getY() - center.getY(), target.getX() - center.getX());
        // rotate theta clockwise by 90 degrees
        theta += Math.PI / 2.0;
        // convert from radians to degrees
        double angle = Math.toDegrees(theta);
        // convert to positive range [0-360)
        if (angle < 0) {
            angle += 360;
        }
        return angle;
    }

    public int testMethod(int n) {
        return n + n;
    }

    public List<Point2D> getDataList() {
        return this.dataList;
    }

    public Point2D getMinPoint() {
        return this.minPoint;
    }

    public Map<Point2D, Double> getAngleDataMap() {
        return this.angleDataMap;
    }


}
