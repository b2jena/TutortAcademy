package com.tutort.assignments.Practice;

public class CountOdds {
    public static int countOdds(int low, int high) {
        int count = 0;
        for (int i = low; i <= high; i++) {
            if ((i & 1) != 0) {
                ++count;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        int low = 0;
        int high = 100;
        System.out.println(countOdds(low, high));
    }
}
