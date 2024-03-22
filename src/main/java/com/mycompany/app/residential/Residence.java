package com.mycompany.app.residential;

import com.mycompany.app.properties.Address;
import com.mycompany.app.properties.Coordinates;
import com.mycompany.app.properties.Neighbourhood;
import com.mycompany.app.properties.Property;
import com.mycompany.app.schools.PhoneNumber;

import java.util.List;

public class Residence extends Property {
    private int accountNumber;
    private final List<AssessmentClass> assessementClassList;
    private int assessedValue;


    public Residence(int accountNumber, List<AssessmentClass> assessmentClassList, int assessedValue,
                     Neighbourhood neighbourhood, Coordinates coordinates) {

        super(null,coordinates, neighbourhood);

        this.accountNumber = accountNumber;
        this.assessementClassList = assessmentClassList;
        this.assessedValue = assessedValue;
    }
}
