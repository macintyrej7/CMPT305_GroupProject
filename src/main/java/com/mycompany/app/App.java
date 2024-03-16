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
import com.esri.arcgisruntime.geometry.*;
import com.esri.arcgisruntime.mapping.view.*;
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import java.awt.Dimension;
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.mapping.BasemapStyle;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Viewpoint;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class App extends Application {

    private MapView mapView;
    private ArcGISMap map;

    private ListenableFuture<IdentifyGraphicsOverlayResult> identifyGraphics;

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

        // Load objects from FXML file into scenes and then into tabs (this can be grouped into a function I think)
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/reports.fxml")); // Make sure to have / in front of file name
        Scene reportsScene = new Scene(fxmlLoader.load());

        FXMLLoader mapFxmlLoader = new FXMLLoader(App.class.getResource("/mapScreen.fxml"));
        Scene mapScene = new Scene(mapFxmlLoader.load());

        FXMLLoader settingsFxmlLoader = new FXMLLoader(App.class.getResource("/settings.fxml"));
        Scene settingsScene = new Scene(settingsFxmlLoader.load());

        Tab tab1 = new Tab("Map", mapScene.getRoot());
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
        map = new ArcGISMap(BasemapStyle.ARCGIS_IMAGERY);
        mapView.setMap(map);

        // Set Default location
        mapView.setViewpoint(new Viewpoint(53.5409, -113.5084, 122227.638572));
        // Set to StackPane inside tab
        StackPane mapStackPane = (StackPane) mapScene.lookup("#mapPane"); // This is how to lookup elements
        mapStackPane.getChildren().add(mapView);                                 // You set the ID under code in SceneBuilder

        // create a graphics overlay and add it to the map view
        GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
        mapView.getGraphicsOverlays().add(graphicsOverlay);

        // create a point geometry with a geo-coords, Detail can be added to Point/Graphics via attribute dictionary
        Graphic pointGraphic = createPointGraphic(-113.6243, 53.5221);
        pointGraphic.getAttributes().put("name", "West Edmonton Mall");

        // add the point graphic to the graphics overlay
        graphicsOverlay.getGraphics().add(pointGraphic);


        mapView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.isStillSincePress()) {
                // create a point from location clicked
                Point2D mapViewPoint = new Point2D(mouseEvent.getX(), mouseEvent.getY());

                // identify graphics on the graphics overlay
                identifyGraphics = mapView.identifyGraphicsOverlayAsync(graphicsOverlay, mapViewPoint, 5, false);

                identifyGraphics.addDoneListener(() -> Platform.runLater(this::createGraphicDialog));
            }
        });

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


    /**
     * Indicates when a graphic is clicked by showing an Alert.
     */
    private void createGraphicDialog() {

        try {
            // get the list of graphics returned by identify
            IdentifyGraphicsOverlayResult result = identifyGraphics.get();
            List<Graphic> graphics = result.getGraphics();

            if (!graphics.isEmpty()) {
                // show an alert dialog box if a graphic was returned
                var dialog = new Alert(Alert.AlertType.INFORMATION);
                dialog.initOwner(mapView.getScene().getWindow());
                dialog.setHeaderText(null);
                dialog.setTitle("Information Dialog Sample");
                // Should map Graphic name to school/data here to retrieve its details
                dialog.setContentText("Clicked on " + graphics.get(0).getAttributes().get("name") + " graphic");
                dialog.showAndWait();
            }
        } catch (Exception e) {
            // on any error, display the stack trace
            e.printStackTrace();
        }
    }



    public Graphic createPointGraphic(double latitude, double longitude){
        Point point = new Point(latitude, longitude, SpatialReferences.getWgs84());
        // create an opaque orange point symbol with a opaque blue outline symbol
        SimpleMarkerSymbol simpleMarkerSymbol =
                new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.ORANGE, 14);
        SimpleLineSymbol blueOutlineSymbol =
                new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLUE, 2);

        simpleMarkerSymbol.setOutline(blueOutlineSymbol);

        // create a graphic with the point geometry and symbol
        Graphic pointGraphic = new Graphic(point, simpleMarkerSymbol);

        return pointGraphic;
    }
}
