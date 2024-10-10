package org.prolific.dev.model.ui;

import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.prolific.dev.model.math.Point;


public class CustomLabel extends AnchorPane {

    private Point point;
    private final Label label = new Label();
    private boolean selected = false;

    public CustomLabel() {
        setup();
    }
    public CustomLabel(Point point) {
        this.point = point;
        setup();
        label.setText(point.toString());
    }

    private void setup() {
        this.getChildren().add(label);
        this.setDefaultStyle();
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handleClick());
        this.setFocusTraversable(true);

        AnchorPane.setLeftAnchor(this, 0.0);
        AnchorPane.setRightAnchor(this, 0.0);
    }

    private void handleClick() {
        this.selected = !this.selected;

        if (this.selected)
            setSelectedStyle();
        else
            setDefaultStyle();
    }

    public boolean isSelected() { return this.selected; }

    public Point getPoint() { return this.point; }
    public String getText() {
        return label.getText();
    }

    public void setText(String text) {
        label.setText(text);
    }

    private void setDefaultStyle() {
        this.setStyle("-fx-background-color: white; -fx-border-color: grey; -fx-border-width: 1; -fx-border-radius: 1;");
        this.label.setStyle("-fx-text-fill: black;");
    }

    private void setSelectedStyle() {
        this.setStyle("-fx-background-color: blue; -fx-border-color: grey; -fx-border-width: 1; -fx-border-radius: 1;");
        this.label.setStyle("-fx-text-fill: white");
    }
}
