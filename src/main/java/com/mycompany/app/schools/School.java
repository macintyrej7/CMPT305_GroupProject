package com.mycompany.app.schools;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class School {

    private int schoolNumber;
    private String schoolName;
    private Address schoolAddress;
    private PhoneNumber schoolPhoneNumber;
    private String schoolEmail;
    private String schoolWebsite;
    private List<String> schoolGradeLevels;
    private boolean spanishBilingual;
    private boolean frenchImmersion;
    private Coordinates schoolCoordinates;

    public School(int schoolNumber, String schoolName, Address schoolAddress,
                  Coordinates schoolCoordinates, List<String> schoolGradeLevels, boolean spanishBilingual,
                  boolean frenchImmersion, String schoolEmail, PhoneNumber schoolPhoneNumber,
                  String schoolWebsite) {

        this.schoolNumber = schoolNumber;
        this.schoolName = schoolName;
        this.schoolAddress = schoolAddress;
        this.schoolCoordinates = schoolCoordinates;
        this.schoolGradeLevels = schoolGradeLevels;
        this.spanishBilingual = spanishBilingual;
        this.frenchImmersion = frenchImmersion;
        this.schoolEmail = schoolEmail;
        this.schoolPhoneNumber = schoolPhoneNumber;
        this.schoolWebsite = schoolWebsite;
    }


    public int getSchoolNumber() {
        return this.schoolNumber;
    }

    public String getSchoolName() {
        return this.schoolName;
    }

    public Address getSchoolAddress() {
        return this.schoolAddress;
    }

    public Coordinates getSchoolCoordinates() {
        return this.schoolCoordinates;
    }

    public List<String> getSchoolGradeLevels() {
        return List.copyOf(schoolGradeLevels);
    }

    public boolean isSpanishBilingual() {
        return this.spanishBilingual;
    }

    public boolean isFrenchImmersion() {
        return this.frenchImmersion;
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

    @Override
    public String toString() {

        StringBuilder schoolString = new StringBuilder();

        schoolString.append("School Name: " + schoolName + "\n");
        schoolString.append("School Number: " + schoolNumber + "\n");
        schoolString.append("School Address: " + schoolAddress.toString() + "\n");
        schoolString.append("School Phone Number: " + schoolPhoneNumber.toString() + "\n");
        schoolString.append("School Email: " + schoolEmail + "\n");
        schoolString.append("School Website: " + schoolWebsite + "\n");
        schoolString.append("School Grade Levels: " + schoolGradeLevels.toString() + "\n");
        schoolString.append("French Immersion: " + frenchImmersion + "\n");
        schoolString.append("Spanish Bilingual: " + spanishBilingual + "\n");
        schoolString.append("School Coordinates: " + schoolCoordinates.toString() + "\n");

        return schoolString.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof School school)) return false;
        return schoolNumber == school.schoolNumber && spanishBilingual == school.spanishBilingual && frenchImmersion == school.frenchImmersion && schoolName.equals(school.schoolName) && schoolAddress.equals(school.schoolAddress) && schoolCoordinates.equals(school.schoolCoordinates) && schoolGradeLevels.equals(school.schoolGradeLevels) && schoolEmail.equals(school.schoolEmail) && schoolPhoneNumber.equals(school.schoolPhoneNumber) && schoolWebsite.equals(school.schoolWebsite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(schoolNumber, schoolName, schoolAddress, schoolCoordinates, schoolGradeLevels, spanishBilingual, frenchImmersion, schoolEmail, schoolPhoneNumber, schoolWebsite);
    }
}

