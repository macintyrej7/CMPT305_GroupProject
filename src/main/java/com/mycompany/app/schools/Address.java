package com.mycompany.app.schools;

import java.util.Objects;

public class Address {
    private String buildingNumber;
    private String streetName;

    public Address(String buildingNumber, String streetName){
        this.buildingNumber = buildingNumber;
        this.streetName = streetName;
    }

    @Override
    public String toString() {

        String addressReturnString = this.buildingNumber + " " + this.streetName;
        return addressReturnString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address that = (Address) o;
        return buildingNumber.equals(that.buildingNumber) && streetName.equals(that.streetName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buildingNumber, streetName);
    }
}