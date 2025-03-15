package com.tutort.assignments.Practice;

public class CountConsistentStrings {
    public static int countConsistentStrings(String allowed, String[] words) {
        int counter = 0;
        for (String s : words) {
            boolean isValid = true;
            for (char ch : s.toCharArray()) {
                if (!allowed.contains(String.valueOf(ch))) {
                    isValid = false;
                    break;
                }
            }
            if (isValid) counter++;
        }

        return counter;
    }

    public static void main(String[] args) {
        String allowed = "ab";
        String[] words = {"ad", "bd", "aaab", "baa", "badab"};
        System.out.println(countConsistentStrings(allowed, words));
    }
}
