package com.mycompany.app.utilities;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class PopupHelper {

    /**
     * Method which Creates a javaFX button styled with some CSS.
     * onAction events should be set in the context which the button is instantiated
     * @return
     */
    public static Button generateCloseButton(){
        SVGPath closeIcon = new SVGPath();
        closeIcon.setContent("M 4.5 4.5 L 11.5 11.5 M 11.5 4.5 L 4.5 11.5");
        closeIcon.setStroke(Color.BLACK);
        closeIcon.setStrokeWidth(2);
        Button closeButton = new Button();
        closeButton.setGraphic(closeIcon);
        closeButton.setStyle("-fx-background-color: transparent;");
        HBox closeButtonBox = new HBox(closeButton);
        closeButtonBox.setStyle("-fx-alignment: center-right; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 5, 0, 0, 1);");
        return closeButton;
    }

    public static Label createLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333333;");
        return label;
    }

    public static VBox createCategoryVbox(String categoryHeading, String categoryInfo, boolean addSpace){
        VBox contentBox = new VBox();
        Label languageLabel = new Label(categoryHeading);
        String relatedInfoStyle = "-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: midnightblue;";;
        languageLabel.setStyle(relatedInfoStyle);
        contentBox.getChildren().add(languageLabel);
        contentBox.getChildren().add(createLabel(categoryInfo));
        if (addSpace)
            contentBox.getChildren().add(createLabel(""));
        return contentBox;
    }
}
