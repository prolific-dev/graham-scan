package com.prolificdev.util;

import com.prolificdev.models.Converter;
import com.prolificdev.models.Point;
import javafx.geometry.Point2D;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class MyData {
    private List<Point2D> inner = new ArrayList<>();
    private List<Point2D> hull = new ArrayList<>();

    public MyData(File file) throws IOException {
        GrahamScan grahamScan = getGrahamScan(getGeomCalcData(getConverterData(file)));
        this.inner = setInnerList(grahamScan.getInner());
        this.hull = setHullList(grahamScan.getOuter());

    }

    private Set<Point> getConverterData(File file) throws IOException {
        return new Converter(file).getConvertedDataSet();
    }

    private Map<Point2D, Double> getGeomCalcData(List<Point2D> point2DList) {
        return new GeomCalc(point2DList).getAngleDataMap();
    }

    private GrahamScan getGrahamScan(Map<Point2D, Double> angleDataMap) {
        return new GrahamScan(angleDataMap);
    }

    private List<Point2D> setInnerList(Map<Point2D, Double> innerData) {
        List<Point2D> list = new ArrayList<>();
        innerData.forEach((key, value) -> list.add(key));
        return list;
    }

    private List<Point2D> setHullList(Stack<Map.Entry<Point2D, Double>> hullData) {
        List<Point2D> list = new ArrayList<>();
        hullData.stream().map(Map.Entry::getKey).forEach(list::add);
        return list;
    }

    public List<Point2D> getInnerList() {
        return this.inner;
    }

    public List<Point2D> getHullList() {
        return this.hull;
    }
}
