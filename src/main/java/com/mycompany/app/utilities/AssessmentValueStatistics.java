package com.mycompany.app.utilities;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class AssessmentValueStatistics {
    private double average;
    private double max;
    private double min;
    private double median;
    private int size;


        public AssessmentValueStatistics(double average, double max, double min, double median, int size){
            this.average = average;
            this.max = max;
            this.min = min;
            this.median = median;
            this.size = size;
        }

    public double getAverage() {
        return average;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

    public double getMedian() {
        return median;
    }

    public int getSize(){
            return size;
    }

    @Override
    public String toString() {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.CANADA);

        return "average: " + currencyFormat.format(average) +"\n" +
                "max: " + currencyFormat.format(max) +"\n" +
                "min: " + currencyFormat.format(min) +"\n" +
                "median: " + currencyFormat.format(median) + "\n" +
                "number of residences: " + size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AssessmentValueStatistics that)) return false;
        return Double.compare(that.average, average) == 0 && Double.compare(that.max, max) == 0 &&
                Double.compare(that.min, min) == 0 && Double.compare(that.median, median) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(average, max, min, median);
    }
}
