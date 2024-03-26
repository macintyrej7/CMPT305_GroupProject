package com.mycompany.app;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Popup;

import java.util.List;

public class CustomPopup extends Popup {
    private double xOffset = 0;
    private double yOffset = 0;

    public CustomPopup() {
        VBox root = new VBox();
        // mint green + rounded edges
        root.setStyle("-fx-padding: 10px; -fx-background-color: rgba(118, 194, 175, 0.8); -fx-background-radius: 10;");

        // Add close button
        SVGPath closeIcon = new SVGPath();
        closeIcon.setContent("M 4.5 4.5 L 11.5 11.5 M 11.5 4.5 L 4.5 11.5");
        closeIcon.setStroke(Color.BLACK);
        closeIcon.setStrokeWidth(2);
        Button closeButton = new Button();
        closeButton.setGraphic(closeIcon);
        closeButton.setStyle("-fx-background-color: transparent;");
        closeButton.setOnMouseClicked(event -> hide());
        HBox closeButtonBox = new HBox(closeButton);
        closeButtonBox.setStyle("-fx-alignment: center-right;");
        root.getChildren().add(closeButtonBox);
        closeButton.setOnAction(event -> this.hide());

        // Add event handlers for mouse press and drag events
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            setX(event.getScreenX() - xOffset);
            setY(event.getScreenY() - yOffset);
        });
        this.getContent().add(root);
    }

    public void setContent(List<VBox> nodes) {
        VBox root = (VBox) this.getContent().get(0);
        root.getChildren().addAll(nodes); // Add new nodes
    }
}
