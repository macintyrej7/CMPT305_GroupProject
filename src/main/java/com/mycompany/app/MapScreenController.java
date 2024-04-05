/**
 * Authors: Legan Hunter-Mutima, Brian Lin, Jason MacIntyre, Sankalp Shrivastav
 * Course: CMPT 305 AS01
 * Instructor: Dr. Indratmo
 * Assignment: Group project
 * Program name: 'MapScreenController.java'
 * Program description: this program initializes the Map tab scene and provides interactive functionality for its
 * various features.
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
import com.mycompany.app.utilities.AssessmentValueStatistics;
import com.mycompany.app.utilities.Calculations;
import com.mycompany.app.utilities.Extractors;
import com.mycompany.app.utilities.PopupHelper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class MapScreenController {

    private final long MAX_VALUE = 2000000;
    private final long MIN_VALUE = 100000;
    private final Color PUBLIC_COLOR = Color.rgb(47,79,79, 0.5);
    private final Color CATHOLIC_COLOR =  Color.rgb(240,230,140, 0.7);
    private final Color CLICKED_COLOR = Color.CRIMSON;

    private MapView mapView;
    private ArcGISMap map;

    private List<School> schoolList;
    private List<Residence> residenceList;


    private List<CustomPopup> popupList;

    private Graphic lastClickedSchoolGraphic = null; // Used to keep track of point colors.

    private ListenableFuture<IdentifyGraphicsOverlayResult> identifyGraphics;

    private RadioButton publicButton, catholicButton, allButton;

    @FXML
    public Label languageProgramsLabel, appliedFiltersLabel, resultsReturnedLabel;
    @FXML
    private StackPane mapPane;


    @FXML
    private ListView<String> gradeFilterListView;

    @FXML
    private ListView<String> languageFilterListView;

    @FXML
    private Slider propertyRadiusSlider;

    @FXML VBox schoolBoardBox;

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


        // Some initialization for filter elements
        ToggleGroup schoolBoardToggleGroup = new ToggleGroup();
        allButton = new RadioButton("All");
        publicButton = new RadioButton("Public");
        catholicButton = new RadioButton("Catholic");
        Circle publicCircle = new Circle(10, PUBLIC_COLOR);
        Circle catholicCircle = new Circle(10, CATHOLIC_COLOR);
        allButton.setToggleGroup(schoolBoardToggleGroup);
        publicButton.setToggleGroup(schoolBoardToggleGroup);
        catholicButton.setToggleGroup(schoolBoardToggleGroup);
        publicButton.setPrefWidth(100);
        catholicButton.setPrefWidth(100);
        allButton.setSelected(true);
        HBox publicRadioBox = new HBox(publicButton, publicCircle);
        HBox catholicRadioBox = new HBox(catholicButton, catholicCircle);
        publicRadioBox.setAlignment(Pos.CENTER_LEFT);
        catholicRadioBox.setAlignment(Pos.CENTER_LEFT);
        schoolBoardBox.getChildren().addAll(allButton, publicRadioBox,catholicRadioBox);

        gradeFilterListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        schoolList = ImportSchools.readCSV("Data/merged_school_data.csv");
        List<String> grades = Extractors.uniqueListValues(schoolList, School::getSchoolGradeList);

        gradeFilterListView.getItems().addAll(grades);

        List<String> languagePrograms = Extractors.uniqueListValues(schoolList, School::getSchoolLanguageList);
        languageFilterListView.getItems().addAll(languagePrograms);

        residenceList = ImportResidences.readCSVResidentialBetweenValues("Data/Property_Assessment_Data_2024.csv", MIN_VALUE, MAX_VALUE);

        popupList = new ArrayList<>();

        drawSchools(schoolList, graphicsOverlay);
        resultsReturnedLabel.setText(String.valueOf(schoolList.size()) + " Results");


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

        // Add zoom reset / home button to the map
        VBox legendUIBox = new VBox(); legendUIBox.setSpacing(5);
        legendUIBox.setMaxWidth(200);
        legendUIBox.setMaxHeight(200);
        VBox legendContent = createLegend();
        Button homeButton = getZoomButton();
        homeButton.setOnAction(e -> resetZoom());
        legendUIBox.getChildren().addAll(legendContent, homeButton);
        StackPane.setAlignment(legendUIBox, Pos.TOP_LEFT);
        mapPane.getChildren().addAll(legendUIBox);
    }

    private VBox createLegend(){
       VBox legendUIContainer = new VBox();
       legendUIContainer.setMaxWidth(200);
       Label publicLabel = new Label("Public");
       publicLabel.setStyle("-fx-font-size: 16px;");
       Circle publicCircle = new Circle(10, PUBLIC_COLOR);
       Label catholicLabel = new Label("Catholic");
       catholicLabel.setStyle("-fx-font-size: 16px;");
       Circle catholicCircle = new Circle(10, CATHOLIC_COLOR);
       publicLabel.setPrefWidth(60);
       catholicLabel.setPrefWidth(60);

        HBox catholicBox = new HBox(10, catholicLabel,catholicCircle);
       HBox publicBox = new HBox(10, publicLabel,publicCircle);
       catholicBox.setAlignment(Pos.CENTER_LEFT);
       publicBox.setAlignment(Pos.CENTER_LEFT);
       BackgroundFill backgroundFill = new BackgroundFill(Color.rgb(125,125,125,0.3), CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY);
       Background background = new Background(backgroundFill);

       legendUIContainer.setBackground(background);
       legendUIContainer.getChildren().addAll(publicBox, catholicBox);
       return legendUIContainer;
    }


    private Button getZoomButton(){
        Button zoomOutButton = new Button("Reset Zoom \u21BB");
        zoomOutButton.setStyle("-fx-background-color: #ffffff; " +
                "-fx-background-radius: 5px; " +
                "-fx-border-color: #333333; " +
                "-fx-border-radius: 5px; " +
                "-fx-padding: 8px 12px; " +
                "-fx-font-size: 20px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: #333333; " +
                "-fx-cursor: hand;");


        return zoomOutButton;
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
        mapView.setViewpoint(new Viewpoint(targetPoint, 3000)); // 10,000 is the scale
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

                // Surrounding Property Calculation based on slider radius
                AssessmentValueStatistics assessmentValueStatistics = Calculations.calculateAssessmentValueStatistics(
                        residenceList,sliderValue,schoolCoordinates);

                String schoolName = (String) clickedGraphic.getAttributes().get("name");
                String calcText = "Assessment Value Statistics within " + sliderValue + " KM: ";
                VBox calcBox = PopupHelper.createCategoryVbox(calcText, assessmentValueStatistics.toString(), true);

                String schoolType = (String) clickedGraphic.getAttributes().get("school type");
                School theSchool = findSchoolByName(schoolName);

                // Zoom on school click
                moveToTargetPoint((Double) clickedGraphic.getAttributes().get("Y"), (Double) clickedGraphic.getAttributes().get("X"));

                // Create Custom popup for school info
                double yOffset = mapPane.localToScene(mapPane.getBoundsInLocal()).getCenterY();
                CustomPopup schoolPopup = new CustomPopup(mapView.getScene().getWindow(), 0,yOffset / 2);

                List<VBox> schooLabelList = theSchool.convertToStyledLabelsGrouped();
                // Buttons formatting
                HBox schoolButtonLayout = new HBox();
                schoolButtonLayout.setSpacing(10);
                Button schoolWebsiteButton = CustomPopup.generateURLButton(theSchool.getSchoolWebsite());
                Button schoolGoogleButton = CustomPopup.generateURLButton("https://www.google.com/search?q=" + schoolNameToQuery(schoolName + " School"));
                //ImageView googleIcon = new ImageView("file:googs.png");
                //googleIcon.setFitHeight(schoolGoogleButton.getHeight());
                //googleIcon.setFitWidth(schoolGoogleButton.getWidth());
                //schoolGoogleButton.setGraphic(googleIcon);
                schoolGoogleButton.setText("Google");
                schoolWebsiteButton.setText("\uD83C\uDF10" + "Website ");
                schoolPopup.addNodes(schooLabelList);
                schoolPopup.addNode(calcBox);
                schoolButtonLayout.getChildren().addAll(schoolGoogleButton, schoolWebsiteButton);
                schoolPopup.addNode(schoolButtonLayout);


                hidePopups(); // Hide any existing popups and replace them with the new one
                popupList.add(schoolPopup);
                schoolPopup.show();

                // modify color on click && reset old clicked point
                if (lastClickedSchoolGraphic!= null){
                    changeGraphicColor(
                            lastClickedSchoolGraphic,
                            decideColor(schoolType)
                    );
                }
                changeGraphicColor(clickedGraphic, CLICKED_COLOR);
                lastClickedSchoolGraphic = clickedGraphic;

            }
        } catch (Exception e) {
            // on any error, display the stack trace
            e.printStackTrace();
        }
    }

    private String schoolNameToQuery(String schoolName){
        String[] schoolNameSplit = schoolName.split(" ");
        return String.join("+", schoolNameSplit);
    }



    private School findSchoolByName(String schoolName){
        return schoolList.stream().filter(school -> school.getSchoolName().equals(schoolName)).toList().get(0);
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
     * @return java.graphics Color
     */
    public Color decideColor(String schoolType){
        schoolType = schoolType.toLowerCase();
        double opacity = 0.5;
        if(schoolType.equals("public")){
            return PUBLIC_COLOR; // gray

        }
        else if(schoolType.equals("catholic")){
            return CATHOLIC_COLOR; // yellow
        }
        return Color.ORANGE;
    }


    public Graphic createPointGraphic(double latitude, double longitude, String name, Color pointColor){
        Point point = new Point(longitude, latitude, SpatialReferences.getWgs84());
        // create an opaque orange point symbol with a opaque blue outline symbol
        SimpleMarkerSymbol simpleMarkerSymbol =
                new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, pointColor, 13);
        SimpleLineSymbol blueOutlineSymbol =
                new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.rgb(0, 0, 0, 0.2), 1);
        simpleMarkerSymbol.setOutline(blueOutlineSymbol);

        // create a graphic with the point geometry and symbol
        Graphic pointGraphic = new Graphic(point, simpleMarkerSymbol);
        pointGraphic.getAttributes().put("name", name);
        pointGraphic.getAttributes().put("latitude", latitude);
        pointGraphic.getAttributes().put("longitude", longitude);
        return pointGraphic;
    }


    public void onMapApplyButtonClick() {

        Predicate<School> publicPred = school -> school.getSchoolType().equals("Public");
        Predicate<School> catholicPred = school -> school.getSchoolType().equals("Catholic");
        Predicate<School> allPred = school -> school.equals(school);
        Predicate<School> finalPred = school -> school.equals(school);

        Predicate<School> gradePred = school -> {
            List<String> selectedGrades = new ArrayList<>(gradeFilterListView.getSelectionModel().getSelectedItems());
            return selectedGrades.isEmpty() || school.getSchoolGradeList().stream().anyMatch(selectedGrades::contains);
        };

        Predicate<School> languagePred = school -> {
            List<String> selectedLanguages= new ArrayList<>(languageFilterListView.getSelectionModel().getSelectedItems());
            return selectedLanguages.isEmpty() || school.getSchoolLanguageList().stream().anyMatch(selectedLanguages::contains);
        };



        if (publicButton.isSelected()) {
            finalPred = finalPred.and(publicPred);
        }
        if (catholicButton.isSelected()) {
            finalPred = finalPred.and(catholicPred);
        }

        if (!gradeFilterListView.getSelectionModel().getSelectedItems().isEmpty()) {
            finalPred = finalPred.and(gradePred);
        }

        if (!languageFilterListView.getSelectionModel().getSelectedItems().isEmpty()) {
            finalPred = finalPred.and(languagePred);
        }

        if (    // No Criteria selected.
                gradeFilterListView.getSelectionModel().getSelectedItems().isEmpty()
                        && languageFilterListView.getSelectionModel().getSelectedItems().isEmpty()
                        && !publicButton.isSelected()
                        && !catholicButton.isSelected()
        ){
            finalPred = allPred;
        }


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
        lastClickedSchoolGraphic = null;
        appliedFiltersLabel.setText("Applied filters: ");
        resetSchoolMap();
        hidePopups(); // clear any active popups
        resetZoom();
        propertyRadiusSlider.setValue(propertyRadiusSlider.getMin()); // set slider back to default pos.
        sliderValue = propertyRadiusSlider.getValue();
        languageFilterListView.getSelectionModel().clearSelection();
        gradeFilterListView.getSelectionModel().clearSelection();
        publicButton.setSelected(false);
        catholicButton.setSelected(false);
        allButton.setSelected(true);
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
