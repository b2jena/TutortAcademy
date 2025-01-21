package com.tutort.assignments.Streams;

import java.util.stream.Stream;

public class PeekStream {
    public static void main(String[] args) {
        /**
         * peek() exists mainly to support debugging, where you want to see the elements as they flow past a certain point in a pipeline. peek() is mainly used for debugging or observing elements.
         */
        Stream.of("one", "two", "three", "four")
                .filter(e -> e.length() > 3)
                .peek(e -> System.out.println("Filtered value: " + e))
                .map(String::toUpperCase)
                .peek(e -> System.out.println("Mapped value: " + e))
                .toList();
    }
}
