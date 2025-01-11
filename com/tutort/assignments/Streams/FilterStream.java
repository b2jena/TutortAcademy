package com.tutort.assignments.Streams;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilterStream {
    public static void main(String[] args) {
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Stream<Integer> multipleoftwo = integerStream.filter((Integer i) -> i % 2 == 0);
        System.out.println(multipleoftwo.toList());
    }
}
