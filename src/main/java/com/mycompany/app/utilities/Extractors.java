package com.mycompany.app.utilities;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Extractors {
    public static <T> List<String> uniqueListValues(List<T> objects, Function<T, List<String>> listExtractor) {
        Set<String> uniqueValues = new HashSet<>();

        for (T obj : objects) {
            List<String> values = listExtractor.apply(obj);
            uniqueValues.addAll(values);
        }

        Comparator<String> customComparator = (s1, s2) -> {
            if (s1.equals("K")) return -1;
            if (s2.equals("K")) return 1;

            try {
                int num1 = Integer.parseInt(s1);
                int num2 = Integer.parseInt(s2);
                return Integer.compare(num1, num2);
            } catch (NumberFormatException e) {
                return s1.compareTo(s2);
            }
        };

        return uniqueValues.stream()
                .sorted(customComparator)
                .collect(Collectors.toList());
    }
}
