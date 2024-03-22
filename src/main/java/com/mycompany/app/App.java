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



import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.mapping.view.*;

import java.awt.Dimension;

import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.mycompany.app.schools.School;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class App extends Application {

    private MapView mapView;

    public ArcGISMap map;

    private ListenableFuture<IdentifyGraphicsOverlayResult> identifyGraphics;

    private List<School> schoolList;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

        stage.setTitle("Exploring Edmonton - A CMPT305 Endeavor");
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        double screenSizeWidth = screenSize.getWidth();
        double screenSizeHeight = screenSize.getHeight() * 0.90;

        // Load objects from FXML file into scenes and then into tabs
        Scene tabLayout = loadSceneFromFXML("tabLayout.fxml");

        stage.setScene(tabLayout);
        stage.setHeight(screenSizeHeight);
        stage.setWidth(screenSizeWidth);
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

    public Scene loadSceneFromFXML(String fxmlFileName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/" + fxmlFileName)); // Make sure to have / in front of file name
        return new Scene(fxmlLoader.load());
    }
}
