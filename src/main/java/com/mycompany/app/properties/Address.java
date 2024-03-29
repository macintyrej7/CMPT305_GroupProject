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

package com.mycompany.app.properties;

import java.util.Objects;

public class Address {
    private String schoolAddress;
    private String postalCode;

    public Address(String schoolAddress, String postalCode){
        this.schoolAddress = schoolAddress;
        this.postalCode = postalCode;
    }

    public String getSchoolAddress() {
        return schoolAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public String toString() {

        String addressReturnString = this.schoolAddress + " " + this.postalCode;
        return addressReturnString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address that = (Address) o;
        return schoolAddress.equals(that.schoolAddress) && postalCode.equals(that.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(schoolAddress, postalCode);
    }
}