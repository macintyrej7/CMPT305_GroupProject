package com.mycompany.app.properties;

import java.util.Objects;

public class Neighbourhood {
    private String neighbourhood;
    private String ward;


    public Neighbourhood(String neighbourhood, String ward){
        this.neighbourhood = neighbourhood;
        this.ward = ward;
    }

    public String getNeighbourhoodName(){
        String returnNeighbourHoodName = this.neighbourhood;
        return returnNeighbourHoodName;
    }

    public String getWard(){
        String returnWardName = this.ward;
        return returnWardName;
    }

    @Override
    public String toString() {

        return this.neighbourhood + " (" + this.ward + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Neighbourhood that = (Neighbourhood) o;
        return neighbourhood.equals(that.neighbourhood) && ward.equals(that.ward);
    }

    @Override
    public int hashCode() {

        return Objects.hash(neighbourhood, ward);
    }
}
