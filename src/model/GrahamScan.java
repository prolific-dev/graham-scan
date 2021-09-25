package model;

import javafx.geometry.Point2D;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;


public class GrahamScan {
    Map<Point2D, Double> inner;
    Stack<Map.Entry<Point2D, Double>> undefined;
    Stack<Map.Entry<Point2D, Double>> outer;
    Point2D minPoint;

    public GrahamScan(Map<Point2D, Double> anglePointMap) {
        PreScan preScan = new PreScan(anglePointMap);
        this.undefined = mapToStack(preScan.getUndefined());
        this.outer = new Stack<>();
        this.inner = preScan.getInner();
        this.minPoint = preScan.getMinPoint();
    }

    private Stack<Map.Entry<Point2D, Double>> graham() {
        return null;
    }


    private Stack<Map.Entry<Point2D, Double>> mapToStack(Map<Point2D, Double> undefinedMap) {
        Set<Map.Entry<Point2D, Double>> set = undefinedMap.entrySet();
        Stack<Map.Entry<Point2D, Double>> stack = new Stack<>();

        set.stream()
                .collect(Collectors.collectingAndThen(Collectors.toList(),
                        lst -> {
                            Collections.reverse(lst);
                            return lst.stream();
                        }))
                .forEach(stack::push);

        return stack;
    }


    public Stack<Map.Entry<Point2D, Double>> getUndefined() {
        return this.undefined;
    }


    public Map<Point2D, Double> getInner() {
        return this.inner;
    }

    public Stack<Map.Entry<Point2D, Double>> getOuter() {
        return this.outer;
    }


}
