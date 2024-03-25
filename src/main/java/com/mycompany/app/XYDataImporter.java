package com.mycompany.app;

import com.mycompany.app.residential.Residence;

import java.util.*;

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

        Optional<Long> maxNum = residenceRawList.stream()
                .map(Residence::getAssessedValue)
                .max(Long::compare);

        return Arrays.asList(minNum.get(), maxNum.get());
    }

    // divide data in n containers
    // numberOfData/n = increment
    // if less than n(increment) add in container

    // eg:  <500{100,120,156,...}, <1000{700, 770, 899,...}, 1500, ...

    // creates empty containers with incremented values of the loaded list
    public HashMap<Long, Integer> incrementContainers(Integer nContainers){

        long incrementVal = (this.minAndMax().get(1) - this.minAndMax().get(0))/nContainers;
       HashMap<Long, Integer> containers = new HashMap<>();

       for (int i = 1; i <= nContainers; i++){
            containers.put(i * incrementVal, null);
       }
        return containers;
    }


}
