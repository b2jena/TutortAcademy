package com.tutort.assignments.Streams;

import java.util.stream.Stream;

public class LimitStream {
    public static void main(String[] args) {
        Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10) // stream create
                .filter(i -> i % 2 == 0) // intermediate operation -> even filter
                .limit(2) // only first 2: 2 and 4 [intermediate operation]
                .forEach(i -> System.out.print(i + " ")); // output -> 2 4
        /**
         * When working with infinite streams, limit() can be very useful for truncating a stream into a finite one.
         * In this example, we’re truncating an infinite stream of numbers into a stream with only ten even numbers.
         */
        Stream.iterate(0, i -> i + 1)
                .filter(i -> i % 2 == 0)
                .limit(10) // first 10 even number starting from 1
                .forEach(System.out::println);
    }
}
