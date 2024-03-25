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
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.IdentifyGraphicsOverlayResult;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.mycompany.app.properties.Coordinates;
import com.mycompany.app.residential.Residence;
import com.mycompany.app.schools.School;
import com.mycompany.app.utilities.Calculations;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class MapScreenController {

    private long MAX_VALUE = 500000;

    private MapView mapView;
    private ArcGISMap map;

    private List<School> schoolList;
    private List<Residence> residenceList;


    private List<CustomPopup> popupList;

    private Graphic lastClickedSchoolGraphic = null; // Used to keep track of point colors.

    private ListenableFuture<IdentifyGraphicsOverlayResult> identifyGraphics;

    @FXML
    public Label languageProgramsLabel, appliedFiltersLabel, resultsReturnedLabel;
    @FXML
    private StackPane mapPane;
    @FXML
    private CheckBox spanishCheckbox, frenchCheckbox, publicCheckbox, catholicCheckbox;

    @FXML
    private ListView<String> gradeFilterListView;

    @FXML
    private Slider propertyRadiusSlider;

    private double sliderValue;





    public void initialize() throws IOException {


        // Set API KEY
        String yourApiKey = "AAPKcf22eb129c224bb3bcf55014b784da1fvhWq8R48qV0B_h9dyoMOy0fHMJoqSE2f12Lxud4ty8-Fzm8WjkoTFpNb1wek2rJS";
        ArcGISRuntimeEnvironment.setApiKey(yourApiKey);

        // create a MapView and set style, add it to the stack pane
        mapView = new MapView();
        map = new ArcGISMap(BasemapStyle.OSM_NAVIGATION);
        mapView.setMap(map);

        // Set Default location, zoom and UI elements position.
        resetZoom();
        sliderValue = propertyRadiusSlider.getValue();

        // Set to StackPane inside tab
        mapPane.getChildren().add(mapView);

        // create a graphics overlay and add it to the map view
        GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
        mapView.getGraphicsOverlays().add(graphicsOverlay);

        List<String> grades = List.of("K", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
        gradeFilterListView.getItems().addAll(grades);
        gradeFilterListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        schoolList = ImportSchools.readCSV("merged_file.csv");
        residenceList = ImportResidences.readCSV("Property_Assessment_Data_2024.csv", MAX_VALUE);

        popupList = new ArrayList<>();

        drawSchools(schoolList, graphicsOverlay);
        resultsReturnedLabel.setText(String.valueOf(schoolList.size()) + " Results");

        // Add zoom reset / home button to the map
        Button homeButton = new Button("\uD83C\uDFE0");
        StackPane.setMargin(homeButton, new Insets(0, 10, -450, 0)); // Adjust margin as needed
        StackPane.setAlignment(homeButton, Pos.CENTER_RIGHT);
        homeButton.setOnAction(e -> resetZoom());
        mapPane.getChildren().add(homeButton);

        // Handle map click events
        mapView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.isStillSincePress()) {
                // create a point from location clicked
                double clickedX, clickedY;
                clickedX = mouseEvent.getX();
                clickedY = mouseEvent.getY();
                Point2D mapViewPoint = new Point2D(clickedX, clickedY);


                // identify graphics on the graphics overlay
                identifyGraphics = mapView.identifyGraphicsOverlayAsync(graphicsOverlay, mapViewPoint, 5, false);
                identifyGraphics.addDoneListener(() -> Platform.runLater(this::handlePointClick));

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
            Graphic schoolGraphic = createPointGraphic(schoolX, schoolY, school.getSchoolName(), decideColor(school.getSchoolType()));
            schoolGraphic.getAttributes().put("school info", school.toString());
            schoolGraphic.getAttributes().put("school type", school.getSchoolType());
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
     * Method which handles a point click event on the map.
     * Creates a popup and applies cosmetic changes to clicked point.
     */
    private void handlePointClick() {

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

                // TODO: Modify radius based on slider here.
                String averageValue = Calculations.CalculateAverageAssessmentValue(residenceList,sliderValue,schoolCoordinates);

                String schoolName = (String) clickedGraphic.getAttributes().get("name") + " School";
                String contentText = (String) clickedGraphic.getAttributes().get("school info") + "Average Value within " + sliderValue + " KM: " + averageValue;
                String schoolType = (String) clickedGraphic.getAttributes().get("school type");

                // Zoom on school click
                moveToTargetPoint((Double) clickedGraphic.getAttributes().get("Y"), (Double) clickedGraphic.getAttributes().get("X"));

                // Create Custom popup for school info
                CustomPopup schoolPopup = new CustomPopup();
                double sceneX = mapPane.localToScene(mapPane.getBoundsInLocal()).getCenterX();
                double yOffset = mapPane.localToScene(mapPane.getBoundsInLocal()).getCenterY() / 1.7;
                double sceneY = mapPane.localToScene(mapPane.getBoundsInLocal()).getMaxY() - yOffset;
                schoolPopup.setContent(schoolName, contentText);

                hidePopups();
                popupList.add(schoolPopup);
                schoolPopup.show(mapView.getScene().getWindow(), 0, sceneY - 300);

                // modify color on click && reset old clicked point
                if (lastClickedSchoolGraphic!= null){
                    changeGraphicColor(
                            lastClickedSchoolGraphic,
                            decideColor(schoolType)
                    );
                }
                changeGraphicColor(clickedGraphic, Color.RED);
                lastClickedSchoolGraphic = clickedGraphic;

            }
        } catch (Exception e) {
            // on any error, display the stack trace
            e.printStackTrace();
        }
    }

    private void changeGraphicColor(Graphic graphic, Color color) {
        if (graphic.getSymbol() instanceof SimpleMarkerSymbol originalSymbol) {
            SimpleMarkerSymbol newSymbol = new SimpleMarkerSymbol(originalSymbol.getStyle(), color, originalSymbol.getSize());
            SimpleLineSymbol blackOutlineSymbol =
                    new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.rgb(0, 0, 0, 0.5), 1);
            newSymbol.setOutline(blackOutlineSymbol);
            graphic.setSymbol(newSymbol);
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

    public void hidePopups(){
        for (CustomPopup popup: popupList){
            popup.hide();
        }
        popupList.clear();
    }

    /**
     * Decide a color for a school point graphic based on its school Type.
     * @param schoolType
     * @return
     */
    public Color decideColor(String schoolType){
        schoolType = schoolType.toLowerCase();
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

        Predicate<School> gradePred = school -> {
            List<String> selectedGrades = new ArrayList<>(gradeFilterListView.getSelectionModel().getSelectedItems());
            return selectedGrades.isEmpty() || school.getSchoolGradeList().stream().anyMatch(selectedGrades::contains);
        };

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

        if (!gradeFilterListView.getSelectionModel().getSelectedItems().isEmpty()) {
            finalPred = finalPred.and(gradePred);
        }

        appliedFiltersLabel.setText(appliedFiltersString);

        getMapOverlay().getGraphics().clear();
        List<School> filteredSchools = schoolList.stream().filter(finalPred).toList();
        resultsReturnedLabel.setText(String.valueOf(filteredSchools.size()) + " Results");
        lastClickedSchoolGraphic = null; // Reset last clicked if screen re-drawn with new.
        sliderValue = propertyRadiusSlider.getValue(); // Get new slider value
        hidePopups(); // Similarly, hide popups when new filter criteria are applied
        drawSchools(filteredSchools, getMapOverlay()); // Draw schools with new filter critiera
        resetZoom();

    }


    public void onResetButtonClick(){
        spanishCheckbox.setSelected(false);
        frenchCheckbox.setSelected(false);
        catholicCheckbox.setSelected(false);
        publicCheckbox.setSelected(false);
        lastClickedSchoolGraphic = null;
        appliedFiltersLabel.setText("Applied filters: ");
        // TODO: Add line for Property slider reset
        resetSchoolMap();
        hidePopups(); // clear any active popups
        resetZoom();
        propertyRadiusSlider.setValue(propertyRadiusSlider.getMin()); // set slider back to default pos.
        sliderValue = propertyRadiusSlider.getValue();
    }

    public void resetZoom(){
        mapView.setViewpoint(new Viewpoint(53.5000, -113.4909, 220000));
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
