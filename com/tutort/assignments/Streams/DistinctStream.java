package com.tutort.assignments.Streams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DistinctStream {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("abc juice");
        list.add("abc juice");
        list.add("abc juice 01");
        Stream<String> stream = list.stream(); // create stream
        System.out.println(stream.distinct().toList()); // intermediate + terminal operation
        Stream<String> stringStream = list.stream();
        stringStream.distinct().forEach(System.out::println);

        // create stream
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5);
        // intermediate operation -> this give a stream only
        Stream<Integer> distinctIntegers = integerStream.distinct();
        // terminal operation and print the output
        System.out.println(distinctIntegers.toList());
    }
}
