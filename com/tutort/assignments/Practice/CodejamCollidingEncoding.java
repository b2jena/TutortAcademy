package com.tutort.assignments.Practice;

import java.util.HashMap;
import java.util.Scanner;

public class CodejamCollidingEncoding {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt(); // number of test cases
        for (int i = 1; i <= t; i++) {
            int[] map = new int[26]; // initialize mapping array
            for (int j = 0; j < 26; j++) {
                map[j] = in.nextInt();
            }

            int n = in.nextInt(); // number of words to encode
            in.nextLine(); // consume newline
            HashMap<String, Integer> freq = new HashMap<>(); // initialize frequency map
            boolean collision = false; // flag to indicate if collision has been found
            for (int j = 0; j < n; j++) {
                String word = in.nextLine();
                StringBuilder sb = new StringBuilder(); // initialize string builder
                for (int k = 0; k < word.length(); k++) {
                    char c = word.charAt(k);
                    sb.append(map[c - 'A']); // map character to digit and append to string builder
                }
                String code = sb.toString(); // get code as string
                if (freq.containsKey(code)) { // check for collision
                    collision = true;
                } else {
                    freq.put(code, 1); // update frequency map
                }
            }
            String result = collision ? "YES" : "NO";
            System.out.println("Case #" + i + ": " + result);
        }
    }
}