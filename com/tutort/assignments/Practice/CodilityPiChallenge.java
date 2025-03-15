package com.tutort.assignments.Practice;

import java.util.HashSet;
import java.util.Set;

public class CodilityPiChallenge {
    public static void main(String[] args) {
        String P = "abc";
        String Q = "bcd";
        System.out.println(solution(P, Q));
    }

    public static int solution(String P, String Q) {
        // Implement your solution here
        Set<Character> distinctLetters = new HashSet<>();
        Set<Character> PdistinctLetters = new HashSet<>();
        Set<Character> QdistinctLetters = new HashSet<>();
        for (int i = 0; i < P.length(); i++) {
            PdistinctLetters.add(P.charAt(i));
        }
        for (int i = 0; i < Q.length(); i++) {
            QdistinctLetters.add(Q.charAt(i));
        }
        for (int i = 0; i < P.length(); i++) {
            distinctLetters.add(P.charAt(i));
            distinctLetters.add(Q.charAt(i));
        }
//        System.out.println(distinctLetters.size());
        if (PdistinctLetters.toString() == QdistinctLetters.toString()) {
            return distinctLetters.size();
        } else {
            System.out.println("in else");
            int n = P.length();
            StringBuilder sb = new StringBuilder();

            // construct S with the maximum possible occurrence of each letter in P and Q
            for (int i = 0; i < n; i++) {
                if (P.charAt(i) == Q.charAt(i)) {
                    sb.append(P.charAt(i));
                } else if (countOccurrences(P, P.charAt(i)) >= countOccurrences(Q, Q.charAt(i))) {
                    sb.append(P.charAt(i));
                } else {
                    sb.append(Q.charAt(i));
                }
            }

            // count the number of distinct letters in S
            Set<Character> distinctLetter = new HashSet<>();
            for (int i = 0; i < sb.length(); i++) {
                distinctLetter.add(sb.charAt(i));
            }

            return distinctLetter.size();
        }
    }

    private static int countOccurrences(String str, char c) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == c) {
                count++;
            }
        }
        return count;
    }
}
