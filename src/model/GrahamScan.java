package model;

import javafx.geometry.Point2D;

import java.util.Map;
import java.util.Stack;


public class GrahamScan {
    Map<Point2D, Double> undefined;
    Map<Point2D, Double> inner;
    Stack<Map.Entry<Point2D, Double>> outer;
    Point2D minPoint;

    public GrahamScan(Map<Point2D, Double> anglePointMap) {
        PreScan preScan = new PreScan(anglePointMap);
        this.inner = preScan.getInner();
        this.outer = preScan.getOuter();
        this.undefined = preScan.getUndefined();
        this.minPoint = preScan.getMinPoint();
    }

    private Stack<Map.Entry<Point2D, Double>> graham() {
        return null;
    }

    /*
    private void addMapToStack(Map<Point2D, Double> anglePointMap) {
        Set<Map.Entry<Point2D, Double>> set = anglePointMap.entrySet();

        set.stream()
                .collect(Collectors.collectingAndThen(Collectors.toList(),
                        lst -> {
                            Collections.reverse(lst);
                            return lst.stream();
                        }))
                .forEach(entry -> this.undefined.push(entry));
    }
     */

    public Map<Point2D, Double> getUndefined() {
        return this.undefined;
    }

    public Map<Point2D, Double> getInner() {
        return this.inner;
    }

    public Stack<Map.Entry<Point2D, Double>> getOuter() {
        return this.outer;
    }


}
