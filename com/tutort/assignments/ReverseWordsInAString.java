package com.tutort.assignments;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ReverseWordsInAString {
    public static String reverseWords(String s) {
        s = s.replaceAll("\\s+", " ").trim();
        List<String> words = Arrays.asList(s.split(" "));
        Collections.reverse(words);
        return String.join(" ", words);
    }

    public static void main(String[] args) {
        String s = "a good   example";
        System.out.println(reverseWords(s));
    }
}
