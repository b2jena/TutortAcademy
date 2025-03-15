package com.tutort.assignments.Practice;

public class CountPoints {
    public static int countPoints(String rings) {
        int[] r = new int[10];
        int[] g = new int[10];
        int[] b = new int[10];
        int n = rings.length();
        for (int i = 0; i < n; i += 2) {
            int a = rings.charAt(i + 1) - '0';
            // whenever rings are present, add it in colour array
            if (rings.charAt(i) == 'R') {
                r[a]++;
            } else if (rings.charAt(i) == 'G') {
                g[a]++;
            } else if (rings.charAt(i) == 'B') {
                b[a]++;
            }
        }
        int count = 0;
        // if all 3 rings are present, ++count
        for (int i = 0; i < 10; i++) {
            if (r[i] > 0 && g[i] > 0 && b[i] > 0) {
                ++count;
            }
        }
        return count;
    }

    public static void main(String[] args) {

    }
}
