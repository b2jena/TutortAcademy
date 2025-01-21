package com.tutort.assignments.Streams;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FlatMapStream {
    public static void main(String[] args) {
        List<List<String>> namesNested = Arrays.asList(
                Arrays.asList("Jeff", "Bezos"),
                Arrays.asList("Bill", "Gates"),
                Arrays.asList("Mark", "Zuckerberg"));

        List<String> namesFlatStream = namesNested.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        System.out.println(namesFlatStream.size() + " " + (namesNested.size() * 2));
        System.out.println(namesFlatStream.stream().toList());
        System.out.println(namesNested.stream().toList());
    }
}
