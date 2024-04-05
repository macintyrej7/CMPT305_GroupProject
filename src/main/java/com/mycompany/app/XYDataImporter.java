package com.mycompany.app;

import com.mycompany.app.residential.Residence;

import java.util.*;
import java.util.stream.Collectors;

public class XYDataImporter {

    private final List<Residence> residenceRawList;
    public   Map<Long, Long> container;
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
    public void incrementContainers(Integer nContainers){

        long incrementVal = (this.minAndMax().get(1) - this.minAndMax().get(0))/nContainers;
       HashMap<Long, Long> containers = new HashMap<>();

       for (int i = 1; i <= nContainers; i++){
            containers.put(i * incrementVal, (long) 0);
       }
        this.container = containers;
    }

    public void updateContainers( Integer nContainer){

        long incrementVal = (this.minAndMax().get(1) - this.minAndMax().get(0))/nContainer;
        Set<Long> keyTest = new HashSet<>();

        this.residenceRawList.forEach(
                residence -> {
                long residenceKey = calcKeyForVal(residence.getAssessedValue(), incrementVal);
                long newResidenceCounter = this.container.get(residenceKey) == null? 0:
                        this.container.get(residenceKey);

                this.container.put(residenceKey, newResidenceCounter + 1);
                    keyTest.add(residenceKey);
                }
        );
    }

    public Map<Long, Long> getContainer() {
        return container;
    }

    public List<Residence> getResidenceRawList() {
        return residenceRawList;
    }

    private long calcKeyForVal(long value, long increment)
    {
        return Math.round((double)value / increment) * increment;
    }

}
