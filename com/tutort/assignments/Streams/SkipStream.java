package com.tutort.assignments.Streams;

import java.util.stream.Stream;

public class SkipStream {
    public static void main(String[] args) {
        System.out.println("skip() demostration:");
        Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10) // stream create
                .filter(i -> i % 2 == 0) // intermediate operation -> even filter
                .skip(2) // skipping 2 and 4 [intermediate operation]
                .forEach(i -> System.out.print(i + " ")); // output -> 6 8 10
        System.out.println("limit() demonstration:");
        Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10) // stream create
                .filter(i -> i % 2 == 0) // intermediate operation -> even filter
                .limit(2) // only first 2: 2 and 4 [intermediate operation]
                .forEach(i -> System.out.print(i + " ")); // output -> 2 4
    }
}
