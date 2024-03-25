package com.mycompany.app.residential;


import com.mycompany.app.properties.Coordinates;
import com.mycompany.app.properties.Neighbourhood;
import com.mycompany.app.properties.Property;


import java.util.List;
import java.util.Objects;

public class Residence extends Property {
    private int accountNumber;
    private final List<AssessmentClass> assessementClassList;
    private long assessedValue;


    public Residence(int accountNumber, List<AssessmentClass> assessmentClassList, int assessedValue,
                     Neighbourhood neighbourhood, Coordinates coordinates) {

        super(null,coordinates, neighbourhood);

        this.accountNumber = accountNumber;
        this.assessementClassList = assessmentClassList;
        this.assessedValue = assessedValue;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public List<AssessmentClass> getAssessementClassList() {
        return assessementClassList;
    }

    public long getAssessedValue() {
        return assessedValue;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Residence residence)) return false;
        if (!super.equals(o)) return false;
        return accountNumber == residence.accountNumber && assessedValue == residence.assessedValue && assessementClassList.equals(residence.assessementClassList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), accountNumber, assessementClassList, assessedValue);
    }

    @Override
    public String toString() {
        return "Residence{" +
                "accountNumber=" + accountNumber +
                ", assessementClassList=" + assessementClassList +
                ", assessedValue=" + assessedValue +
                '}';
    }
}
