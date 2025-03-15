package com.tutort.assignments.Practice;

import java.io.IOException;
import java.util.Scanner;

import static java.lang.System.in;

public class CodejamIlluminationOptimization {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(in);
        int t = scanner.nextInt();
        for (int k = 1; k <= t; k++) {
            int M = scanner.nextInt();
            int R = scanner.nextInt();
            int N = scanner.nextInt();
            int[] X = new int[N];
            for (int i = 0; i < N; i++) {
                X[i] = scanner.nextInt();
            }
            // to-do
        }
        in.close();
    }
}