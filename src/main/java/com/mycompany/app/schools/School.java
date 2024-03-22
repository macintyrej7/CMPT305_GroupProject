package com.mycompany.app.schools;

import com.mycompany.app.properties.Address;
import com.mycompany.app.properties.Coordinates;
import com.mycompany.app.properties.Property;

import java.util.List;
import java.util.Objects;

public class School extends Property{

    private int schoolNumber;
    private String schoolName;
    private PhoneNumber schoolPhoneNumber;
    private String schoolEmail;
    private String schoolWebsite;
    private List<String> schoolGradeLevels;
    private boolean spanishBilingual;
    private boolean frenchImmersion;
    private String schoolType;

    public School(int schoolNumber, String schoolName, Address address,
                  Coordinates coordinates, List<String> schoolGradeLevels, boolean spanishBilingual,
                  boolean frenchImmersion, String schoolEmail, PhoneNumber schoolPhoneNumber,
                  String schoolWebsite, String schoolType) {

        super(address,coordinates,null);

        this.schoolNumber = schoolNumber;
        this.schoolName = schoolName;
        this.schoolGradeLevels = schoolGradeLevels;
        this.spanishBilingual = spanishBilingual;
        this.frenchImmersion = frenchImmersion;
        this.schoolEmail = schoolEmail;
        this.schoolPhoneNumber = schoolPhoneNumber;
        this.schoolWebsite = schoolWebsite;
        this.schoolType = schoolType;
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

    public String getSchoolType() {return this.schoolType;}

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
        schoolString.append("French Immersion: " + frenchImmersion + "\n");
        schoolString.append("Spanish Bilingual: " + spanishBilingual + "\n");
        schoolString.append("School Coordinates: " + getCoordinates() + "\n");

        return schoolString.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof School school)) return false;
        if (!super.equals(o)) return false;
        return schoolNumber == school.schoolNumber && spanishBilingual == school.spanishBilingual && frenchImmersion == school.frenchImmersion && schoolName.equals(school.schoolName) && schoolPhoneNumber.equals(school.schoolPhoneNumber) && schoolEmail.equals(school.schoolEmail) && schoolWebsite.equals(school.schoolWebsite) && schoolGradeLevels.equals(school.schoolGradeLevels) && schoolType.equals(school.schoolType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), schoolNumber, schoolName, schoolPhoneNumber, schoolEmail, schoolWebsite, schoolGradeLevels, spanishBilingual, frenchImmersion, schoolType);
    }


}

