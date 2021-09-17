package model;

import javafx.geometry.Point2D;

import java.util.*;

public class GrahamGeomCalc {
    private static final Point2D ZERO = new Point2D(0, 0);
    private final Point2D minPoint;
    private final List<Point2D> dataList;
    private final Map<Point2D, Double> angleDataMap;


    public GrahamGeomCalc(List<Point2D> input) {
        this.dataList = normalizeInput(input);
        this.minPoint = minPointCalc();
        this.angleDataMap = mapAngleData();

    }

    private List<Point2D> normalizeInput(List<Point2D> input) {
        double minX = ZERO.getX();
        double maxX = ZERO.getX();
        double minY = ZERO.getY();
        double maxY = ZERO.getY();
        List<Point2D> normalizedList = new ArrayList<>();

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
        Point2D minPoint = this.minPoint;
        List<Point2D> dataList = this.dataList;
        Map<Point2D, Double> map = new HashMap<>();

        map.put(minPoint, 0.0);
        dataList.remove(minPoint);

        dataList.forEach(p -> map.put(p, minPoint.angle(p)));

        return map;
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
