/**
 * Authors: Legan Hunter-Mutima, Brian Lin, Jason MacIntyre, Sankalp Shrivastav
 * Course: CMPT 305 AS01
 * Instructor: Dr. Indratmo
 * Assignment: Group project
 * Program name: 'School.java'
 */

package com.mycompany.app.schools;

import com.mycompany.app.properties.Address;
import com.mycompany.app.properties.Coordinates;
import com.mycompany.app.properties.Property;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.mycompany.app.utilities.PopupHelper.createLabel;

public class School extends Property{

    private int schoolNumber;
    private String schoolName;
    private PhoneNumber schoolPhoneNumber;
    private String schoolEmail;
    private String schoolWebsite;
    private List<String> schoolGradeLevels;
    private String schoolType;
    private GradeList schoolGradeList;
    private List<String> schoolLanguageList;


    public School(int schoolNumber, String schoolName, Address address,
                  Coordinates coordinates, List<String> schoolGradeLevels,
                   String schoolEmail, PhoneNumber schoolPhoneNumber,
                  String schoolWebsite, String schoolType, GradeList schoolGradeList,
                  List<String> schoolLanguageList) {

        super(address,coordinates,null);
        //setting neighbourhood null for now, but left it in case we want to use it later

        this.schoolNumber = schoolNumber;
        this.schoolName = schoolName;
        this.schoolGradeLevels = schoolGradeLevels;
        this.schoolEmail = schoolEmail;
        this.schoolPhoneNumber = schoolPhoneNumber;
        this.schoolWebsite = schoolWebsite;
        this.schoolType = schoolType;
        this.schoolGradeList = schoolGradeList;
        this.schoolLanguageList = schoolLanguageList;
    }

    public int getSchoolNumber() {
        return this.schoolNumber;
    }
    public String getSchoolName() {
        return this.schoolName;
    }

    public List<String> getSchoolGradeLevels() {
        return List.copyOf(schoolGradeLevels);
    }

    public String getSchoolEmail() {
        return this.schoolEmail;
    }

    public PhoneNumber getSchoolPhoneNumber() {
        return this.schoolPhoneNumber;
    }

    public String getSchoolWebsite() {
        return this.schoolWebsite;
    }

    public String getSchoolType() {return this.schoolType;}

    public List<String> getSchoolGradeList() {
        return schoolGradeList.getGradeList();
    }

    public List<String> getSchoolLanguageList() {
        return schoolLanguageList;
    }

    @Override
    public String toString() {

        StringBuilder schoolString = new StringBuilder();

        schoolString.append("School Name: " + schoolName + "\n");
        schoolString.append("School Number: " + schoolNumber + "\n");
        schoolString.append("School Type: " + schoolType + "\n");
        schoolString.append("School Address: " + getAddress() + "\n");
        schoolString.append("School Phone Number: " + schoolPhoneNumber.toString() + "\n");
        schoolString.append("School Email: " + schoolEmail + "\n");
        schoolString.append("School Website: " + schoolWebsite + "\n");
        schoolString.append("School Grade Levels: " + schoolGradeLevels.toString() + "\n");
        schoolString.append("School Coordinates: " + getCoordinates() + "\n");
        schoolString.append("School Grade List: " + schoolGradeList + "\n");
        schoolString.append("School Language List: " + schoolLanguageList + "\n");

        return schoolString.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof School school)) return false;
        if (!super.equals(o)) return false;
        return schoolNumber == school.schoolNumber && schoolName.equals(school.schoolName) && schoolPhoneNumber.equals(school.schoolPhoneNumber) && schoolEmail.equals(school.schoolEmail) && schoolWebsite.equals(school.schoolWebsite) && schoolGradeLevels.equals(school.schoolGradeLevels) && schoolType.equals(school.schoolType) && schoolGradeList.equals(school.schoolGradeList) && schoolLanguageList.equals(school.schoolLanguageList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), schoolNumber, schoolName, schoolPhoneNumber, schoolEmail, schoolWebsite, schoolGradeLevels, schoolType, schoolGradeList, schoolLanguageList);
    }

    public List<VBox> convertToStyledLabelsGrouped() {
        List<VBox> vBoxList = new ArrayList<>();

        // School name styled separately
        Label schoolNameLabel = new Label(schoolName);

        Label schoolTypeLabel = new Label(schoolType +  " School");
        schoolNameLabel.setStyle(
                "-fx-font-size: 36px;" +
                " -fx-font-family: 'Tahoma'; -fx-font-weight: bold;" +
                " -fx-text-fill: black;" +
                "-fx-background-radius: 5px;");
        schoolTypeLabel.setStyle("-fx-font-size: 20px; -fx-font-family: 'Tahoma';" +
                " -fx-font-weight: bold; -fx-text-fill: darkviolet;");
        VBox schoolNameBox = new VBox(schoolNameLabel);
        schoolNameBox.getChildren().add(schoolTypeLabel);
        schoolNameBox.getChildren().add(createLabel(""));
        vBoxList.add(schoolNameBox);

        // Group related Info
        String relatedInfoStyle = "-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: midnightblue;";
        // Contact info category
        VBox contactInfoBox = new VBox();
        Label contactInfoLabel = new Label("Contact Information");
        contactInfoLabel.setStyle(relatedInfoStyle);
        contactInfoBox.getChildren().add(contactInfoLabel);
        // Address, Add email, phone number
        contactInfoBox.getChildren().add(createLabel("Address: " + this.getAddress().getAddress()));
        contactInfoBox.getChildren().add(createLabel("Email: " + schoolEmail));
        contactInfoBox.getChildren().add(createLabel("Phone Number: " + schoolPhoneNumber));
        vBoxList.add(contactInfoBox);

        // Language category
        VBox languageBox = new VBox();
        Label languageLabel = new Label("Language Programs");
        languageLabel.setStyle(relatedInfoStyle);
        languageBox.getChildren().add(languageLabel);
        // Add language-related information
        languageBox.getChildren().add(createLabel(schoolLanguageList.toString()));
        vBoxList.add(languageBox);

        // Grade information category
        VBox gradeInfoBox = new VBox();
        Label gradeInfoLabel = new Label("Grade Information");
        gradeInfoLabel.setStyle(relatedInfoStyle);
        gradeInfoBox.getChildren().add(gradeInfoLabel);
        // Add grade-related information
        gradeInfoBox.getChildren().add(createLabel("Grade Levels: " + schoolGradeLevels));
        gradeInfoBox.getChildren().add(createLabel("School Grade List: " + schoolGradeList));
        vBoxList.add(gradeInfoBox);

        // Add more VBox for other categories if needed

        return vBoxList;
    }



}



