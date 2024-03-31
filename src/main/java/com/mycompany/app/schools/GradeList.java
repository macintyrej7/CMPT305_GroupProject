package com.mycompany.app.schools;

import java.util.List;
import java.util.Objects;

public class GradeList {
    private List<String> gradeList;

    public GradeList(List<String> gradeList) {
        this.gradeList = gradeList;
    }

    public List<String> getGradeList() {
        return gradeList;
    }

    @Override
    public String toString() {
        if (gradeList.get(0) == "N/A"){
            return "No grades";
        }
        return gradeList.get(0) + "-" + gradeList.get(gradeList.size() - 1);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GradeList gradeList1)) return false;
        return gradeList.equals(gradeList1.gradeList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gradeList);
    }
}
