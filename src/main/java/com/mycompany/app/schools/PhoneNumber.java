/**
 * Authors: Legan Hunter-Mutima, Brian Lin, Jason MacIntyre, Sankalp Shrivastav
 * Course: CMPT 305 AS01
 * Instructor: Dr. Indratmo
 * Assignment: Group project
 * Program name: 'PhoneNumber.java'
 */

package com.mycompany.app.schools;

import java.util.Objects;

public class PhoneNumber {


    private String areaCode;
    private String phonePrefix;
    private String phoneNumber;

    public PhoneNumber(String input){
        String processedNumber = removeNonIntegers(input);

        if(isValidLength(processedNumber)){
            this.areaCode = processedNumber.substring(0,3);
            this.phonePrefix = processedNumber.substring(3,6);
            this.phoneNumber = processedNumber.substring(6);
        }
        else{
            throw new IllegalArgumentException("Invalid Phone Number");
        }

    }

    private String removeNonIntegers(String input) {
        return input.replaceAll("[^0-9]", "");
    }

    private Boolean isValidLength(String input){
        if(input.length() == 10){
            return true;
        }
        else{
            return false;
        }
    }

    public String getAreaCode() {
        return areaCode;
    }

    @Override
    public String toString() {
        return "(" + this.areaCode + ") " + this.phonePrefix + "-" + this.phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhoneNumber that)) return false;
        return areaCode.equals(that.areaCode) && phonePrefix.equals(that.phonePrefix) && phoneNumber.equals(that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(areaCode, phonePrefix, phoneNumber);
    }
}
