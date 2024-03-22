package com.mycompany.app.properties;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

public class Property{

    private Address address;

    private Coordinates coordinates;

    private Neighbourhood neighbourhood;

    public Property(Address address, Coordinates coordinates, Neighbourhood neighbourhood){
        this.address = address;
        this.coordinates = coordinates;
        this.neighbourhood = neighbourhood;
    }


    public Address getAddress() {
        Address returnAddress = this.address;

        return returnAddress;
    }

    public double getLongitude(){
        double returnLongitude = this.coordinates.getLongitude();
        return returnLongitude;
    }

    public double getLatitude(){
        double returnLatitude = this.coordinates.getLatitude();
        return returnLatitude;
    }

    public Coordinates getCoordinates(){
        Coordinates returnCoordinates = this.coordinates;
        return returnCoordinates;
    }



    @Override
    public String toString() {
        NumberFormat commaFormat = NumberFormat.getInstance(Locale.CANADA);

        return "Address = " + this.address + "\n" +
                "Coordinates = " + this.coordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Property property)) return false;
        return address.equals(property.address) && coordinates.equals(property.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, coordinates);
    }


}
