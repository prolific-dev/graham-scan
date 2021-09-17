import javafx.geometry.Point2D;
import model.Converter;
import model.GrahamGeomCalc;
import model.Scan;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Test {
    private Test() {}

    public static void main(String[] args) throws IOException {
        Converter converter = new Converter(new File("src/data/data.txt"));
        List<Point2D> dataList = converter.getInput();
        GrahamGeomCalc grahamGeomCalc = new GrahamGeomCalc(dataList);

        System.out.println("");

        for (Point2D p : dataList) {
            System.out.println(p.toString());
        }

        System.out.println("");

        System.out.println("Size: " + grahamGeomCalc.getDataList().size());
        //System.out.println(GrahamGeomCalc.getMinPoint(dataList));
        System.out.println(grahamGeomCalc.getMinPoint());
        //System.out.println(GrahamGeomCalc.getMinPoint(dataList).angle(dataList.get(0)));
        System.out.println(grahamGeomCalc.getMinPoint().angle(grahamGeomCalc.getDataList().get(0)));

        System.out.println("");

        Map<Point2D, Double> map = grahamGeomCalc.getAngleDataMap();
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

        System.out.println(grahamGeomCalc.getAngleDataMap().size());

        System.out.println();

        Scan scan = new Scan(grahamGeomCalc.getAngleDataMap());

        System.out.println(scan.getUndefined());
        System.out.println("");
        // System.out.println(scan.getOuter());
        System.out.println("");

        scan.getUndefined().forEach((key, value) -> System.out.println(value));

        System.out.println("");

        System.out.println(scan.getUndefined().size());

        System.out.println(scan.getInner());
        System.out.println("");

        System.out.println("");


    }
}
