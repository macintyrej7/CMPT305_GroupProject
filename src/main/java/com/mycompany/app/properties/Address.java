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
    private String address;
    private String postalCode;

    public Address(String address, String postalCode){
        this.address = address;
        this.postalCode = postalCode;
    }

    public String getAddress() {
        return this.address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public String toString() {

        String addressReturnString = this.address + " " + this.postalCode;
        return addressReturnString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address that = (Address) o;
        return address.equals(that.address) && postalCode.equals(that.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, postalCode);
    }
}