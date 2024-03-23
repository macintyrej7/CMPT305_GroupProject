/**
 * Authors: Legan Hunter-Mutima, Brian Lin, Jason MacIntyre, Sankalp Shrivastav
 * Course: CMPT 305 AS01
 * Instructor: Dr. Indratmo
 * Assignment: Group project
 * Due date: ???
 * Last worked on: Mar 21, 2024
 * Program name:
 * Program description:
 */

package com.mycompany.app;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.BasemapStyle;
import com.esri.arcgisruntime.mapping.popup.Popup;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.IdentifyGraphicsOverlayResult;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.esri.arcgisruntime.symbology.TextSymbol;
import com.mycompany.app.properties.Coordinates;
import com.mycompany.app.residential.Residence;
import com.mycompany.app.schools.School;
import com.mycompany.app.utilities.Calculations;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class MapScreenController {

    private MapView mapView;
    private ArcGISMap map;

    private List<School> schoolList;
    private List<Residence> residenceList;

    private boolean popupShowing = false;
    private List<CustomPopup> popupList;

    private ListenableFuture<IdentifyGraphicsOverlayResult> identifyGraphics;

    @FXML
    public Label languageProgramsLabel, appliedFiltersLabel, resultsReturnedLabel;
    @FXML
    private StackPane mapPane;
    @FXML
    private CheckBox spanishCheckbox, frenchCheckbox, publicCheckbox, catholicCheckbox;





    public void initialize() throws IOException {


        // Set API KEY
        String yourApiKey = "AAPKcf22eb129c224bb3bcf55014b784da1fvhWq8R48qV0B_h9dyoMOy0fHMJoqSE2f12Lxud4ty8-Fzm8WjkoTFpNb1wek2rJS";
        ArcGISRuntimeEnvironment.setApiKey(yourApiKey);

        // create a MapView and set style, add it to the stack pane
        mapView = new MapView();
        map = new ArcGISMap(BasemapStyle.OSM_NAVIGATION);
        mapView.setMap(map);

        // Set Default location
        mapView.setViewpoint(new Viewpoint(53.5000, -113.4909, 220000));

        // Set to StackPane inside tab
        mapPane.getChildren().add(mapView);

        // create a graphics overlay and add it to the map view
        GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
        mapView.getGraphicsOverlays().add(graphicsOverlay);


        schoolList = ImportSchools.readCSV("Edmonton_Schools_Merged - Mar_21_2024.csv");
        residenceList = ImportResidences.readCSV("Property_Assessment_Data_2024.csv");

        popupList = new ArrayList<>();

        drawSchools(schoolList, graphicsOverlay);
        resultsReturnedLabel.setText(String.valueOf(schoolList.size()) + " Results");

        mapView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.isStillSincePress()) {
                // create a point from location clicked
                double clickedX, clickedY;
                clickedX = mouseEvent.getX();
                clickedY = mouseEvent.getY();
                Point2D mapViewPoint = new Point2D(clickedX, clickedY);


                // identify graphics on the graphics overlay
                identifyGraphics = mapView.identifyGraphicsOverlayAsync(graphicsOverlay, mapViewPoint, 5, false);
                identifyGraphics.addDoneListener(() -> Platform.runLater(this::createGraphicDialog));

            }
        });
    }


    /**
     * Method which takes a list of object w/ coordinates and draws them to the graphics overlay.
     * @param tschoolList
     * @param graphicsOverlay
     */
    private void drawSchools(List<School> tschoolList, GraphicsOverlay graphicsOverlay){
        double schoolX, schoolY;
        for (School school : tschoolList) {
            schoolX = school.getCoordinates().getLatitude();
            schoolY = school.getCoordinates().getLongitude();
            Graphic schoolGraphic = createPointGraphic(schoolX, schoolY, school.getSchoolName(), decideColor(school));
            schoolGraphic.getAttributes().put("SCHOOL", school.toString());
            schoolGraphic.getAttributes().put("X", schoolX);
            schoolGraphic.getAttributes().put("Y", schoolY);

            graphicsOverlay.getGraphics().add(schoolGraphic);
        }
    }


    private void moveToTargetPoint(double x, double y) {
        // Create a point representing the location you want to move to
        Point targetPoint = new Point(x, y, SpatialReference.create(4326));

        // Set the viewpoint to center on the target point
        mapView.setViewpoint(new Viewpoint(targetPoint, 10000)); // 10,000 is the scale
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


                // Should map Graphic name to school/data here to retrieve its details
                Graphic clickedGraphic = graphics.get(0);

                double schoolLatitude = (Double) clickedGraphic.getAttributes().get("X");
                double schoolLongitude = (Double) clickedGraphic.getAttributes().get("Y");
                Coordinates schoolCoordinates = new Coordinates(schoolLatitude, schoolLongitude);

                String averageValue = Calculations.CalculateAverageAssessmentValue(residenceList,2.0,schoolCoordinates);

                String schoolName = (String) clickedGraphic.getAttributes().get("name") + " School";
                String contentText = (String) clickedGraphic.getAttributes().get("SCHOOL") + "Average Value within 2.0 KM: " + averageValue;

                // Zoom on school click
                //moveToTargetPoint((Double) clickedGraphic.getAttributes().get("Y"), (Double) clickedGraphic.getAttributes().get("X"));


                // Create Custom popup for school info
                CustomPopup schoolPopup = new CustomPopup();
                double sceneX = mapPane.localToScene(mapPane.getBoundsInLocal()).getMinX();
                double sceneY = mapPane.localToScene(mapPane.getBoundsInLocal()).getMinY();
                schoolPopup.setContent(schoolName, contentText);
                if (!popupIsShowing()) {
                    popupList.add(schoolPopup);
                    schoolPopup.show(mapView.getScene().getWindow(), sceneX, sceneY);
                }


            }
        } catch (Exception e) {
            // on any error, display the stack trace
            e.printStackTrace();
        }
    }

    public boolean popupIsShowing(){
        for (CustomPopup popup: popupList){
            if (popup.isShowing()){
                return true;
            }
        }
        return false;
    }

    /**
     * Decide a color for a school point graphic based on its attributes.
     * @param schoolObj
     * @return
     */
    public Color decideColor(School schoolObj){
        String schoolType = schoolObj.getSchoolType().toLowerCase();
        double opacity = 0.2;
        if(schoolType.equals("public")){
            return Color.rgb(0, 0, 128, opacity); // purple

        }
        else if(schoolType.equals("catholic")){
            return Color.rgb(255, 215, 0, opacity); // yellow
        }
        return Color.ORANGE;
    }


    public Graphic createPointGraphic(double latitude, double longitude, String name, Color pointColor){
        Point point = new Point(longitude, latitude, SpatialReferences.getWgs84());
        // create an opaque orange point symbol with a opaque blue outline symbol
        SimpleMarkerSymbol simpleMarkerSymbol =
                new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, pointColor, 14);
        SimpleLineSymbol blueOutlineSymbol =
                new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.rgb(0, 0, 0, 0.5), 1);
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
        Predicate<School> publicPred = school -> school.getSchoolType().equals("Public");
        Predicate<School> catholicPred = school -> school.getSchoolType().equals("Catholic");
        Predicate<School> allPred = school -> school.equals(school);
        Predicate<School> finalPred = school -> school.equals(school);

        String appliedFiltersString = "Applied Filters: ";

        if (spanishCheckbox.isSelected()){
            appliedFiltersString = appliedFiltersString + "\nSPANISH";
            finalPred = finalPred.and(spanishPred);
        }
        if (frenchCheckbox.isSelected()){
            appliedFiltersString = appliedFiltersString + "\nFRENCH";
            finalPred = finalPred.and(frenchPred);
        }

        if (publicCheckbox.isSelected()) {
            appliedFiltersString = appliedFiltersString + "\nPUBLIC";
            finalPred = finalPred.and(publicPred);
        }
        if (catholicCheckbox.isSelected()) {
            appliedFiltersString = appliedFiltersString + "\nCATHOLIC";
            finalPred = finalPred.and(catholicPred);
        }

        if (!frenchCheckbox.isSelected() && !spanishCheckbox.isSelected() && !publicCheckbox.isSelected() && !catholicCheckbox.isSelected()){
            //showingString = showingString + "";
            finalPred = allPred;
        }

        appliedFiltersLabel.setText(appliedFiltersString);

        getMapOverlay().getGraphics().clear();
        List<School> filteredSchools = schoolList.stream().filter(finalPred).toList();
        resultsReturnedLabel.setText(String.valueOf(filteredSchools.size()) + " Results");
        drawSchools(filteredSchools, getMapOverlay());
    }


    public void onResetButtonClick(){
        spanishCheckbox.setSelected(false);
        frenchCheckbox.setSelected(false);
        catholicCheckbox.setSelected(false);
        publicCheckbox.setSelected(false);
        appliedFiltersLabel.setText("Applied filters: ");
        // Add line for Property slider
        resetSchoolMap();
    }


    public void resetSchoolMap(){
        getMapOverlay().getGraphics().clear();
        resultsReturnedLabel.setText(String.valueOf(schoolList.size()) + " Results");
        drawSchools(schoolList, getMapOverlay());
    }


    public GraphicsOverlay getMapOverlay(){
        return mapView.getGraphicsOverlays().get(0);
    }



}
