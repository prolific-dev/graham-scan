import javafx.geometry.Point2D;
import model.Converter;
import model.GeomCalc;
import model.GrahamScan;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Test {
    private Test() {}

    public static void main(String[] args) throws IOException {
        Converter converter = new Converter(new File("src/data/data.txt"));
        List<Point2D> dataList = converter.getInput();
        GeomCalc geomCalc = new GeomCalc(dataList);

        System.out.println("");

        for (Point2D p : dataList) {
            System.out.println(p.toString());
        }

        System.out.println("");

        System.out.println("Size: " + geomCalc.getDataList().size());
        //System.out.println(GeomCalc.getMinPoint(dataList));
        System.out.println(geomCalc.getMinPoint());
        //System.out.println(GeomCalc.getMinPoint(dataList).angle(dataList.get(0)));
        System.out.println(geomCalc.getMinPoint().angle(geomCalc.getDataList().get(0)));

        System.out.println("");

        Map<Point2D, Double> map = geomCalc.getAngleDataMap();
        Set<Map.Entry<Point2D, Double>> set = map.entrySet();
        set.forEach(mp -> {
            System.out.print(mp.getKey() + ": ");
            System.out.println(mp.getValue());
        });

        System.out.println("");

        Stack<Point2D> stack = new Stack<>();
        set.stream()
                .collect(Collectors.collectingAndThen(Collectors.toList(),
                        lst -> {
                            Collections.reverse(lst);
                            return lst.stream();
                        }))
                .forEach(p -> stack.push(p.getKey()));

        for (Point2D p : stack) {
            System.out.println(p.toString());
        }

        System.out.println("");

        System.out.println(geomCalc.getAngleDataMap().size());

        System.out.println();

        GrahamScan grahamScan = new GrahamScan(geomCalc.getAngleDataMap());

        System.out.print("Stack:");
        System.out.println(grahamScan.getUndefined());
        System.out.println("");
        // System.out.println(grahamScan.getOuter());
        System.out.println("");

        // **** grahamScan.getUndefined().forEach((key, value) -> System.out.println(value));

        System.out.println("");
        System.out.print("Stack-Size: ");
        System.out.println(grahamScan.getUndefined().size());
        System.out.println("");
        System.out.print("Inner: ");
        System.out.println(grahamScan.getInner());
        System.out.println("");
        System.out.println(grahamScan.getUndefined().peek());
        System.out.println("");

        Point2D a = new Point2D(1, 1);
        Point2D b = new Point2D(4, 4);
        Point2D c = new Point2D(3, 3);

        System.out.println(grahamScan.direction(a, b, c));
        System.out.println("");
        System.out.println("");

        //grahamScan.graham();

        System.out.println(grahamScan.getUndefined());
        System.out.println(grahamScan.getInner());
        System.out.println(grahamScan.getOuter());
    }
}
