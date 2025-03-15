package com.tutort.assignments.Practice;

import java.util.Arrays;
import java.util.Scanner;

public class CandidateCode {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int x = scanner.nextInt();

        // Read the energy levels of all the animals
        long[] animals = new long[n];
        for (int i = 0; i < n; i++) {
            animals[i] = scanner.nextLong();
        }

        // Set the range of possible energy levels
        long left = Arrays.stream(animals).min().getAsLong();
        long right = Arrays.stream(animals).max().getAsLong();

        // Binary search for the minimum energy level P
        long ans = -1;
        while (left <= right) {
            long mid = (left + right) / 2;
            int count = 0;
            for (long a : animals) {
                if (a >= mid) {
                    count++;
                }
            }
            if (count >= x) {
                left = mid + 1;
                ans = mid;
            } else {
                right = mid - 1;
            }
        }

        // Print the answer
        System.out.println(ans);
    }
}
