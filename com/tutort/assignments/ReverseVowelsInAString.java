package com.tutort.assignments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ReverseVowelsInAString {
    public static void main(String[] args) {
        String string = "leetcode";
        char[] chars = new char[string.length()];
        int vowelslength = 0;
        int i;
        for (i = 0; i < string.length(); i++) {
            chars[i] = string.charAt(i);
            if (chars[i] == 'a' || chars[i] == 'e' || chars[i] == 'i' || chars[i] == 'o' || chars[i] == 'u') {
                ++vowelslength;
            }
        }
        System.out.println(Arrays.toString(chars));
        System.out.println(vowelslength);
        char[] vowels = new char[vowelslength];
        int j = 0;
        for (i = 0; i < string.length(); i++) {
            if (chars[i] == 'a' || chars[i] == 'e' || chars[i] == 'i' || chars[i] == 'o' || chars[i] == 'u') {
                vowels[j] = chars[i];
                System.out.println(vowels[j] + " " + j);
                ++j;
            }
        }
        System.out.println(Arrays.toString(vowels) + " " + j);
        for (i = 0; i < string.length(); i++) {
            if (chars[i] == 'a' || chars[i] == 'e' || chars[i] == 'i' || chars[i] == 'o' || chars[i] == 'u') {
                chars[i] = vowels[j - 1];
                --j;
            }
        }
        String reversed = Arrays.toString(chars);
        System.out.println(reversed);
    }

    public String reverseVowels(String s) {
        char[] arr = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            arr[i] = s.charAt(i);
        }
        List<Character> vowels = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            if ((s.charAt(i) == 'a' || s.charAt(i) == 'e' || s.charAt(i) == 'i' || s.charAt(i) == 'o' || s.charAt(i) == 'u') || (s.charAt(i) == 'A' || s.charAt(i) == 'E' || s.charAt(i) == 'I' || s.charAt(i) == 'O' || s.charAt(i) == 'U')) {
                vowels.add(s.charAt(i));
            }
        }
        Collections.reverse(vowels);
        int c = 0;
        for (int i = 0; i < arr.length; i++) {
            if ((s.charAt(i) == 'a' || s.charAt(i) == 'e' || s.charAt(i) == 'i' || s.charAt(i) == 'o' || s.charAt(i) == 'u') || (s.charAt(i) == 'A' || s.charAt(i) == 'E' || s.charAt(i) == 'I' || s.charAt(i) == 'O' || s.charAt(i) == 'U')) {
                arr[i] = vowels.get(c);
                ++c;
            }
        }
        return String.valueOf(arr);
    }
}
