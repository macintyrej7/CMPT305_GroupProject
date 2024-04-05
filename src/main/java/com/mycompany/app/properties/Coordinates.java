/**
 * Authors: Legan Hunter-Mutima, Brian Lin, Jason MacIntyre, Sankalp Shrivastav
 * Course: CMPT 305 AS01
 * Instructor: Dr. Indratmo
 * Assignment: Group project
 * Program name: 'Coordinates.java'
 */

package com.mycompany.app.properties;

import java.util.Objects;

public class Coordinates {

    private double latitude;
    private double longitude;

    public Coordinates(Double latCoord, Double longCoord){
        this.latitude = latCoord;
        this.longitude = longCoord;
    }

    public double getLatitude(){
        double returnLatitude = this.latitude;
        return returnLatitude;
    }

    public double getLongitude(){
        double returnLongitude = this.longitude;
        return returnLongitude;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinates that)) return false;
        return Double.compare(that.latitude, latitude) == 0 && Double.compare(that.longitude, longitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }

    @Override
    public String toString() {

        String coordinateReturn = "(" + this.latitude + ", " + this.longitude + ")";

        return coordinateReturn;
    }
}
