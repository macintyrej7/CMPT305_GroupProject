package com.mycompany.app;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class CustomPopup extends Stage {
    private double xOffset = 0;
    private double yOffset = 0;

    public CustomPopup(double x, double y) {
        this.setX(x);
        this.setY(y);
        initModality(Modality.NONE);
        initStyle(StageStyle.UNDECORATED);
        VBox root = new VBox();
        // mint green + rounded edges
        root.setStyle("-fx-padding: 10px; -fx-background-color: rgb(227, 251, 255, 0.9); -fx-background-radius: 10;");

        // Add close button
        SVGPath closeIcon = new SVGPath();
        closeIcon.setContent("M 4.5 4.5 L 11.5 11.5 M 11.5 4.5 L 4.5 11.5");
        closeIcon.setStroke(Color.BLACK);
        closeIcon.setStrokeWidth(2);
        Button closeButton = new Button();
        closeButton.setGraphic(closeIcon);
        closeButton.setStyle("-fx-background-color: transparent;");
        closeButton.setOnMouseClicked(event -> this.hide());
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
        this.setScene(new javafx.scene.Scene(root));
    }

    public void setContent(List<VBox> nodes) {
        VBox root = (VBox) this.getScene().getRoot();
        root.getChildren().addAll(nodes); // Add new nodes
    }

    public void addNode(Node newNode){
        VBox root = (VBox) this.getScene().getRoot();
        root.getChildren().add(newNode);
    }

    public static Button generateURLButton(String URL){
        Button resultButton = new Button();
        resultButton.setStyle(
                "-fx-background-color: midnightblue;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10px 20px;" +
                        "-fx-border-radius: 5px;" +
                        "-fx-background-radius: 5px;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 5, 0, 0, 1);"
        );
        resultButton.setOnMouseClicked(mouseEvent -> launchURL(URL));
        return resultButton;
    }

    public static void launchURL(String url) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Desktop browsing is not supported.");
            // Handle the case where desktop browsing is not supported
        }
    }
}
