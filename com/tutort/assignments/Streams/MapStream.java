package com.tutort.assignments.Streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class MapStream {
    public static void main(String[] args) {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4);
        List<Integer> transformedElements = integers.stream()
                .map(e -> e * 2)
                .toList(); // terminal operation
        List<Integer> expected = Arrays.asList(2, 4, 6, 8);
        System.out.println(transformedElements + "," + expected);
        List<String> strings = Arrays.asList("apple", "ball", "cat", "dog", "elephant", "fish");
        // intermediate operation
        Stream<String> stringStream = strings.stream().filter(x -> x.length() > 2).map(x -> x.toUpperCase());
        // terminal operation
        List<String> terminalOperationList = stringStream.toList();
        System.out.println(terminalOperationList);
    }
}
