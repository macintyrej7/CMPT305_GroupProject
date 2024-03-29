package com.mycompany.app;

import com.mycompany.app.utilities.PopupHelper;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class CustomPopup extends Stage {
    private double xOffset = 0;
    private double yOffset = 0;

    public CustomPopup(Window owner, double x, double y) {
        this.setX(x);
        this.setY(y);
        initOwner(owner);
        initModality(Modality.NONE);
        initStyle(StageStyle.TRANSPARENT);

        VBox root = new VBox();
        // Set popup CSS styling
        root.setStyle("-fx-padding: 10px; -fx-background-color: rgba(245,255,250, 0.8); -fx-background-radius: 10;");

        // Add close button
        Button closeButton = PopupHelper.generateCloseButton();
        closeButton.setOnMouseClicked(event -> this.hide());
        HBox closeButtonBox = new HBox(closeButton);
        closeButtonBox.setStyle("-fx-alignment: center-right; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 5, 0, 0, 1);");
        root.getChildren().add(closeButtonBox);

        // Add event handlers for mouse press and drag events
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            setX(event.getScreenX() - xOffset);
            setY(event.getScreenY() - yOffset);
        });
        Scene scene = new Scene(root);
        scene.setFill(null);
        this.setScene(scene);
    }

    public void addNodes(List<VBox> nodes) {
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


    // Method to extract background color from CSS style string
    private String getBackgroundColor(String style) {
        String[] parts = style.split(";");
        for (String part : parts) {
            if (part.trim().startsWith("-fx-background-color:")) {
                return part.split(":")[1].trim();
            }
        }
        return "transparent"; // Default transparent color if not found
    }
}
