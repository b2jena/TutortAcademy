package com.tutort.assignments.Practice;/* package codechef; // don't place package name! */
/**
 * author -> b2jena
 * Copyright © 2022 b2jena. All rights reserved.
 * This is my competitive programmming snippet
 */

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class MorphleCodingRound {
    public static void main(String[] args) throws Exception {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        Task solver = new Task();
        int tesCases = 1;
        for (int test = 1; test <= tesCases; test += 1) {
            solver.solve(test, in, out);
        }
        out.close();
    }

    static class Task {
        static final int MOD = (int) (1e9 + 7);
        String pi = "314159265358979323846264338327";

        public static long gcd(long a, long b) {
            return b == 0 ? a : gcd(b, a % b);
        }

        public static void printStrings(String[] inputStrings) {
            int n = inputStrings.length;
            int k = inputStrings[0].length();
            int[] indexes = new int[n];
            Arrays.fill(indexes, 0);

            while (true) {
                StringBuilder outputString = new StringBuilder();
                for (int i = 0; i < n; i++) {
                    outputString.append(inputStrings[i].charAt(indexes[i]));
                }
                System.out.print(outputString + " ");
                int i;
                for (i = 0; i < n; i++) {
                    if (indexes[i] < k - 1) {
                        indexes[i]++;
                        break;
                    } else {
                        indexes[i] = 0;
                    }
                }
                if (i == n) {
                    break;
                }
            }
        }

        public void solve(int testNumber, InputReader in, PrintWriter out) {
            //Your code goes below
            String[] inputStrings = {"abc", "qwe"};
            printStrings(inputStrings);
        }
    }

    static class InputReader {
        public BufferedReader take;
        public StringTokenizer split;

        public InputReader(InputStream stream) {
            take = new BufferedReader(new InputStreamReader(stream), 10000000);
            split = null;
        }

        public String next() {
            while (split == null || !split.hasMoreTokens()) {
                try {
                    split = new StringTokenizer(take.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return split.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        int[] readArray(int n) {
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = nextInt();
            return a;
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }
    }
}
