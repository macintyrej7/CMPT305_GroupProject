package com.mycompany.app;

import com.mycompany.app.residential.Residence;
import com.mycompany.app.schools.School;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class XYDataImporter {

    private final List<Residence> residenceRawList;
//    private List<School> schoolRawList;


    public XYDataImporter(List<Residence> residenceRawList){
        this.residenceRawList = residenceRawList;
    }

    // by min and max
    public List<Long> minAndMax(){

        Optional<Long> minNum = residenceRawList.stream()
                .map(Residence::getAssessedValue)
                .min(Long::compare);

        minNum.ifPresentOrElse(
                num -> System.out.println("Minimum assessed value: " + num),
                () -> System.out.println("No residences found.")
        );

        Optional<Long> maxNum = residenceRawList.stream()
                .map(Residence::getAssessedValue)
                .max(Long::compare);

        return null;
    }

    // divide data in n containers
    // numberOfData/n = increment
    // if less than n(increment) add in container

    // eg:  <500{100,120,156,...}, <1000{700, 770, 899,...}, 1500, ...


}
