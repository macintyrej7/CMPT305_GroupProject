package com.mycompany.app.residential;

import java.util.Objects;

public class AssessmentClass {
    private String assessmentClassName;
    private double assessmentClassPercent;

    public AssessmentClass(String assessmentClass, double percent) {
        this.assessmentClassName = assessmentClass;
        this.assessmentClassPercent = percent;
    }

    public String getAssessmentClassName() {
        return assessmentClassName;
    }

    @Override
    public String toString() {
        return this.assessmentClassName + " " + this.assessmentClassPercent + "%";

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AssessmentClass that)) return false;
        return Double.compare(that.assessmentClassPercent, assessmentClassPercent) == 0 && assessmentClassName.equals(that.assessmentClassName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assessmentClassName, assessmentClassPercent);
    }
}