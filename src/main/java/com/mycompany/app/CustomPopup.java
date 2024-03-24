package com.mycompany.app;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;

public class CustomPopup extends Popup {
    private double xOffset = 0;
    private double yOffset = 0;

    public CustomPopup() {
        VBox root = new VBox();
        // mint green + rounded edges
        root.setStyle("-fx-padding: 10px; -fx-background-color: rgba(118, 194, 175, 0.8); -fx-background-radius: 10;");
        Label titleLabel = new Label();
        Label descriptionLabel = new Label();

        titleLabel.setStyle("-fx-text-fill: #ffffff; " +
                "-fx-font-family: 'Verdana'; " +
                "-fx-font-size: 40px; " +
                "-fx-font-weight: bold;");

        descriptionLabel.setStyle("-fx-text-fill: #ffffff; " +
                "-fx-font-family: 'Arial'; " +
                "-fx-font-size: 18px; " +
                "-fx-font-weight: normal;");

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

        root.getChildren().addAll(titleLabel, descriptionLabel);

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

    public void setContent(String title, String description) {
        VBox root = (VBox) this.getContent().get(0);
        // Update the labels with the provided title and description
        ((Label) root.getChildren().get(1)).setText(title);
        ((Label) root.getChildren().get(2)).setText(description);
    }




}