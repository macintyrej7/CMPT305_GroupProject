package com.mycompany.app;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.BasemapStyle;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.IdentifyGraphicsOverlayResult;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.mycompany.app.schools.School;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

public class MapScreenController {

    private MapView mapView;
    private ArcGISMap map;

    private List<School> schoolList;


    private ListenableFuture<IdentifyGraphicsOverlayResult> identifyGraphics;

    @FXML
    public Label languageProgramsLabel;
    @FXML
    private StackPane mapPane;
    @FXML
    private CheckBox spanishCheckbox, frenchCheckbox;



    public void initialize() throws IOException {


        // Set API KEY
        String yourApiKey = "AAPKcf22eb129c224bb3bcf55014b784da1fvhWq8R48qV0B_h9dyoMOy0fHMJoqSE2f12Lxud4ty8-Fzm8WjkoTFpNb1wek2rJS";
        ArcGISRuntimeEnvironment.setApiKey(yourApiKey);

        // create a MapView and set style, add it to the stack pane
        mapView = new MapView();
        map = new ArcGISMap(BasemapStyle.OSM_NAVIGATION);
        mapView.setMap(map);

        // Set Default location
        mapView.setViewpoint(new Viewpoint(53.5409, -113.5084, 122227.638572));
        // Set to StackPane inside tab
        mapPane.getChildren().add(mapView);

        // create a graphics overlay and add it to the map view
        GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
        mapView.getGraphicsOverlays().add(graphicsOverlay);


        schoolList = ImportSchools.readCSV("Edmonton_Schools_Merged - Mar_21_2024.csv");
        double schoolX, schoolY;
        for (School school : schoolList) {
            schoolX = school.getSchoolCoordinates().getLatitude();
            schoolY = school.getSchoolCoordinates().getLongitude();
            Graphic schoolGraphic = createPointGraphic(schoolX, schoolY, school.getSchoolName());
            schoolGraphic.getAttributes().put("SCHOOL", school.toString());
            graphicsOverlay.getGraphics().add(schoolGraphic);


        }

        mapView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.isStillSincePress()) {
                // create a point from location clicked
                Point2D mapViewPoint = new Point2D(mouseEvent.getX(), mouseEvent.getY());

                // identify graphics on the graphics overlay
                identifyGraphics = mapView.identifyGraphicsOverlayAsync(graphicsOverlay, mapViewPoint, 5, false);

                identifyGraphics.addDoneListener(() -> Platform.runLater(this::createGraphicDialog));
            }
        });
    }

    private void getSchools(List<School> tschoolList, GraphicsOverlay graphicsOverlay){
        double schoolX, schoolY;
        for (School school : tschoolList) {
            schoolX = school.getSchoolCoordinates().getLatitude();
            schoolY = school.getSchoolCoordinates().getLongitude();
            Graphic schoolGraphic = createPointGraphic(schoolX, schoolY, school.getSchoolName());
            schoolGraphic.getAttributes().put("SCHOOL", school.toString());
            graphicsOverlay.getGraphics().add(schoolGraphic);
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
                // Should map Graphic name to school/data here to retrieve its details
                Graphic clickedGraphic = graphics.get(0);
                dialog.setTitle((String) clickedGraphic.getAttributes().get("name") + " School");
                dialog.setContentText((String) clickedGraphic.getAttributes().get("SCHOOL")
                );
                dialog.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                dialog.getDialogPane().setStyle("-fx-font: 16 arial;");

                dialog.showAndWait();
            }
        } catch (Exception e) {
            // on any error, display the stack trace
            e.printStackTrace();
        }

    }

    public Scene loadSceneFromFXML(String fxmlFileName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/" + fxmlFileName)); // Make sure to have / in front of file name
        return new Scene(fxmlLoader.load());
    }


    public Graphic createPointGraphic(double latitude, double longitude, String name){
        Point point = new Point(longitude, latitude, SpatialReferences.getWgs84());
        // create an opaque orange point symbol with a opaque blue outline symbol
        SimpleMarkerSymbol simpleMarkerSymbol =
                new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.DARKSEAGREEN, 14);
        SimpleLineSymbol blueOutlineSymbol =
                new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.DARKSLATEGRAY, 2);
        simpleMarkerSymbol.setOutline(blueOutlineSymbol);

        // create a graphic with the point geometry and symbol
        Graphic pointGraphic = new Graphic(point, simpleMarkerSymbol);
        pointGraphic.getAttributes().put("name", name);
        pointGraphic.getAttributes().put("latitude", latitude);
        pointGraphic.getAttributes().put("longitude", longitude);
        return pointGraphic;
    }


    public void onMapApplyButtonClick() {

        Predicate<School> frenchPred = school -> school.isFrenchImmersion();
        Predicate<School> spanishPred = school -> school.isSpanishBilingual();
        Predicate<School> allPred = school -> school.equals(school);
        Predicate<School> finalPred = school -> school.equals(school);
        if (spanishCheckbox.isSelected()){
            languageProgramsLabel.setText("SHOWING SPANISH");
            finalPred = spanishPred;
        }
        if (frenchCheckbox.isSelected() && !spanishCheckbox.isSelected()){
            languageProgramsLabel.setText("SHOWING FRENCH");
            finalPred = frenchPred;
        }
        if (frenchCheckbox.isSelected() && spanishCheckbox.isSelected()){
            languageProgramsLabel.setText("SHOWING FRENCH + SPANISH");
            finalPred = frenchPred.and(spanishPred);
        }
        if (!frenchCheckbox.isSelected() && !spanishCheckbox.isSelected()){
            languageProgramsLabel.setText("SHOWING ALL");
            finalPred = allPred;
        }
        getMapOverlay().getGraphics().clear();
        List<School> frenchSchools = schoolList.stream().filter(finalPred).toList();
        getSchools(frenchSchools, getMapOverlay());

    }


    public GraphicsOverlay getMapOverlay(){
        return mapView.getGraphicsOverlays().get(0);
    }

}
