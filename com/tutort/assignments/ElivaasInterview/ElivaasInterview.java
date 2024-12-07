package com.tutort.assignments.ElivaasInterview;

import java.util.Arrays;

/**
 * Input: s = "  hello world  "
 * Output: "world hello"
 * Explanation: Your reversed string should not contain leading or trailing spaces.
 * Example 3:
 * <p>
 * Input: s = "a good   example"
 * Output: "example good a"
 * Explanation: You need to reduce multiple spaces between two words to a single space in the reversed string.
 */
public class ElivaasInterview {
    public static void main(String[] args) {
        String s = "a good   example ";
        System.out.println(reverseString(s));

    }

    private static String reverseString(String s) {
        String[] words = splitWords(s);
        // can't use inbuilt functions
        String[] wordsInBuilt = s.split("\\s");
        String reverseds = "";
        // reversing the order of words given
        for (int i = words.length - 1; i >= 0; i--) {
            reverseds += words[i];
            if (i != 0) {
                reverseds += " ";
            }
        }
        return reverseds;
    }

    private static String[] splitWords(String s) {
        int wordCount = countWords(s);
        String[] words = new String[wordCount];
        int counter = 0;
        StringBuilder word = new StringBuilder();
        System.out.println("length of s: " + s.length());
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                if (word.length() > 0) {
                    words[counter] = word.toString();
                    counter += 1;
                    word = new StringBuilder();
                }
            } else {
                word.append(s.charAt(i));
            }
        }
        if (word.length() > 0) {
            words[counter] = word.toString();
        }
        System.out.println(Arrays.toString(words));
        return words;
    }

    private static int countWords(String s) {
        int c = 0;
        boolean flag = false;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                if (flag) {
                    c++;
                    flag = false;
                }
            } else {
                flag = true;
            }
        }
        if (flag) {
            c++;
        }
        System.out.println("word count: " + c);
        return c;
    }
}
