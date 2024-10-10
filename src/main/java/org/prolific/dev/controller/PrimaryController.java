package org.prolific.dev.controller;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.prolific.dev.App;
import org.prolific.dev.model.math.DataSet;
import org.prolific.dev.model.math.Point;
import org.prolific.dev.model.ui.CustomLabel;
import org.prolific.dev.model.ui.CustomLineChart;
import org.prolific.dev.util.Observer;

public class PrimaryController implements Observer {

    private Set<Point> points;
    private DataSet data;
    private Set<CustomLabel> selectedLabels;

    @FXML
    private CustomLineChart lineChart;
    @FXML
    private VBox ds_labels;
    @FXML
    private VBox ch_labels;
    @FXML
    private Button updateButton;
    @FXML
    private Button importButton;
    @FXML
    private Button addPointButton;
    @FXML
    private Button editPointButton;
    @FXML
    private Button deletePointButton;
    @FXML
    private TextField inputField;
    @FXML
    private Text logtext;

    @FXML
    public void initialize() {
        points = new HashSet<>();
        selectedLabels = new HashSet<>();

        data = new DataSet();
        data.add(this);

        inputField.addEventHandler(KeyEvent.KEY_TYPED, event -> {
            addPointButton.setDisable(this.inputField.getText().isEmpty());
            editPointButton.setDisable(true);
            deletePointButton.setDisable(true);
            selectedLabels.clear();
        });

        addVboxMouseClickHandler(ds_labels);
        addVboxMouseClickHandler(ch_labels);

        points.add(new Point(10.5, 20.3));   // Small values
        points.add(new Point(50.7, 80.9));   // Mid-range values
        points.add(new Point(70.2, 60.1));   // Random mid-range values
        points.add(new Point(90.0, 10.5));   // High x, low y
        points.add(new Point(20.8, 30.7));   // Close points in a small cluster
        points.add(new Point(40.5, 50.3));   // Forming a mid-range cluster
        points.add(new Point(10.0, 70.0));   // Low x, high y
        points.add(new Point(90.0, 90.0));   // High x, high y
        points.add(new Point(30.0, 40.0));   // Central point
        points.add(new Point(100.0, 100.0)); // Maximum point on the range

        data.setData(this.points);
    }

    private void addVboxMouseClickHandler(VBox vbox) {
        vbox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Node clickedNode = event.getPickResult().getIntersectedNode();

            while (clickedNode != null && !(clickedNode instanceof CustomLabel))
                clickedNode = clickedNode.getParent();

            if (clickedNode != null) {
                CustomLabel clickedLabel = (CustomLabel) clickedNode;
                if (clickedLabel.isSelected())
                    selectedLabels.add(clickedLabel);
                else
                    selectedLabels.remove(clickedLabel);

                if (selectedLabels.isEmpty())
                {
                    inputField.clear();
                    addPointButton.setDisable(true);
                    editPointButton.setDisable(true);
                    deletePointButton.setDisable(true);
                }
                else if (selectedLabels.size() == 1)
                {
                    inputField.setText(clickedLabel.getText().strip());
                    addPointButton.setDisable(true);
                    editPointButton.setDisable(false);
                    deletePointButton.setDisable(false);
                }
                else {
                    inputField.clear();
                    addPointButton.setDisable(true);
                    editPointButton.setDisable(true);
                    deletePointButton.setDisable(false);
                }
            }
        });
    }

    private Point parseText(String pointString) {
        double x;
        double y;

        String regex = "^([0-9]+\\.[0-9]+),( )*([0-9]+\\.[0-9]+)$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pointString);

        if (matcher.matches())
        {
            x = Double.parseDouble(matcher.group(1));
            y = Double.parseDouble(matcher.group(3));

            return new Point(x, y);
        }

        return null;
    }

    @FXML
    private void addPoint() {
        if (!inputField.getText().isEmpty()) {
            Point p = parseText(inputField.getText());

            if (p != null)
            {
                points.add(p);
                data.setData(points);
                data.notifyObservers();

                inputField.clear();
                addPointButton.setDisable(true);
                editPointButton.setDisable(true);
                deletePointButton.setDisable(true);

                logtext.setText("Added " + p.toString() + "...");
            }
            else {
                logtext.setText("\'" + inputField.getText() + "\' is not a point...");
            }
        }
    }

    @FXML
    private void editPoint() {
        if (selectedLabels.size() == 1 && !inputField.getText().isEmpty())
        {
            String text = inputField.getText();
            Point p = parseText(text);

            if (p != null)
            {
                CustomLabel l = (CustomLabel) selectedLabels.toArray()[0];

                points.remove(l.getPoint());
                points.add(p);

                data.setData(points);
                data.notifyObservers();

                inputField.clear();
                addPointButton.setDisable(true);
                editPointButton.setDisable(true);
                deletePointButton.setDisable(true);

                logtext.setText("Edited " + l.getPoint().toString() + " to " + p.toString() + "...");
            }
            else {
                logtext.setText("\'" + text + "\' is not a point...");
            }

        }
    }

    @FXML
    private void deletePoint() {
        if (!selectedLabels.isEmpty()) {
            Iterator<CustomLabel> iterator = selectedLabels.iterator();

            while (iterator.hasNext()) {
                CustomLabel l = iterator.next();
                points.remove(l.getPoint());
                data.setData(points);
                iterator.remove();
                data.notifyObservers();

                inputField.clear();
                addPointButton.setDisable(true);
                editPointButton.setDisable(true);
                deletePointButton.setDisable(true);

                logtext.setText("Deleted " + l.getPoint().toString() + "...");
            }
        }
    }

    @FXML
    public void updateChartData() {
        data.setData(points);
        lineChart.updateChartData(data.getData(), data.getConvexHull());
        updatePointLabels();
    }

    @FXML
    private void updatePointLabels() {
        ds_labels.getChildren().clear();
        ch_labels.getChildren().clear();

        data.getData().forEach(p -> ds_labels.getChildren().add(new CustomLabel(p)));
        data.getConvexHull().forEach(p -> ch_labels.getChildren().add(new CustomLabel(p)));
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @Override
    public void update() {
        updateChartData();
        updatePointLabels();
    }
}
