package com.mycompany.app.schools;

public class School {
    private int schoolNumber;
    private String schoolName;
    private String schoolType;
    private Address schoolAddress;
    private Coordinates schoolCoordinates;
    private String schooLGradeLevel;
    private boolean hasSpanish;
    private boolean hasFrench;
    private String email;
    private PhoneNumber schoolPhoneNumber;
    private String schoolWebsite;

    public School(int schoolNumber, String schoolName, String schoolType, Address schoolAddress,
                  Coordinates schoolCoordinates, String schooLGradeLevel, boolean hasSpanish,
                  boolean hasFrench, String email, PhoneNumber schoolPhoneNumber,
                  String schoolWebsite) {

        this.schoolNumber = schoolNumber;
        this.schoolName = schoolName;
        this.schoolType = schoolType;
        this.schoolAddress = schoolAddress;
        this.schoolCoordinates = schoolCoordinates;
        this.schooLGradeLevel = schooLGradeLevel;
        this.hasSpanish = hasSpanish;
        this.hasFrench = hasFrench;
        this.email = email;
        this.schoolPhoneNumber = schoolPhoneNumber;
        this.schoolWebsite = schoolWebsite;
    }


    public int getSchoolNumber() {
        return this.schoolNumber;
    }

    public String getSchoolName() {
        return this.schoolName;
    }

    public String getSchoolType() {
        return schoolType;
    }

    public Address getSchoolAddress() {
        return this.schoolAddress;
    }

    public Coordinates getSchoolCoordinates() {
        return this.schoolCoordinates;
    }

    public String getSchooLGradeLevel() {
        return this.schooLGradeLevel;
    }

    public boolean isHasSpanish() {
        return this.hasSpanish;
    }

    public boolean isHasFrench() {
        return this.hasFrench;
    }

    public String getEmail() {
        return this.email;
    }

    public PhoneNumber getSchoolPhoneNumber() {
        return this.schoolPhoneNumber;
    }

    public String getSchoolWebsite() {
        return this.schoolWebsite;
    }
}

