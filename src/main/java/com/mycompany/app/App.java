/**
 * Copyright 2019 Esri
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.mycompany.app;

import java.awt.Dimension;
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.mapping.BasemapStyle;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.MapView;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private MapView mapView;

    public static void main(String[] args) {

        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

        stage.setTitle("Exploring Edmonton - A CMPT305 Endeavor");
        TabPane root = new TabPane();
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        double screenSizeWidth = screenSize.getWidth();
        double screenSizeHeight = screenSize.getHeight() * 0.96;

        // Load objects from FXML file into scenes and then into tabs
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/reports.fxml")); // Make sure to have / in front of file name
        Scene reportsScene = new Scene(fxmlLoader.load());

        FXMLLoader mapFxmlLoader = new FXMLLoader(App.class.getResource("/mapScreen.fxml"));
        Scene otherMapScene = new Scene(mapFxmlLoader.load());

        FXMLLoader settingsFxmlLoader = new FXMLLoader(App.class.getResource("/settings.fxml"));
        Scene settingsScene = new Scene(settingsFxmlLoader.load());

        Tab tab1 = new Tab("Map", otherMapScene.getRoot());
        Tab tab2 = new Tab("Reports"  , reportsScene.getRoot());
        Tab tab3 = new Tab("Settings" , settingsScene.getRoot());

        root.getTabs().add(tab1);
        root.getTabs().add(tab2);
        root.getTabs().add(tab3);

        // Set API KEY
        String yourApiKey = "AAPKcf22eb129c224bb3bcf55014b784da1fvhWq8R48qV0B_h9dyoMOy0fHMJoqSE2f12Lxud4ty8-Fzm8WjkoTFpNb1wek2rJS";
        ArcGISRuntimeEnvironment.setApiKey(yourApiKey);

        // create a MapView to display the map and add it to the stack pane
        mapView = new MapView();
        // Sets map style
        ArcGISMap map = new ArcGISMap(BasemapStyle.ARCGIS_STREETS);
        mapView.setMap(map);
        // Set Default location
        mapView.setViewpoint(new Viewpoint(53.5409, -113.5084, 122227.638572));
        // Set to StackPane inside tab
        StackPane otherStackPane = (StackPane) otherMapScene.lookup("#mapPane"); // This is how to lookup elements
        otherStackPane.getChildren().add(mapView);                                 // You set the ID under code in SceneBuilder

        stage.setScene(new Scene(root, screenSizeWidth, screenSizeHeight));
        stage.show();

    }

    /**
     * Stops and releases all resources used in application.
     */
    @Override
    public void stop() {

        if (mapView != null) {
            mapView.dispose();
        }
    }
}
