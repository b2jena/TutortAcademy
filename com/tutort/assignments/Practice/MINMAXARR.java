package com.tutort.assignments.Practice;

import java.io.*;
import java.util.StringTokenizer;
/* package codechef; // don't place package name! */

/**
 * author -> b2jena
 * Copyright © 2022 b2jena. All rights reserved.
 * credit: https://discuss.codechef.com/t/minmaxarr-editorial/104895
 * Time complexity: O(n) per testcase.
 */
public class MINMAXARR {
    public static void main(String[] args) throws Exception {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        Task solver = new Task();
        int tesCases = in.nextInt();
        for (int test = 1; test <= tesCases; test += 1) {
            solver.solve(test, in, out);
        }
        out.close();
    }

    static class Task {
        static final int MOD = (int) (1e9 + 7);

        public static long gcd(long a, long b) {
            return b == 0 ? a : gcd(b, a % b);
        }

        public void solve(int testNumber, InputReader in, PrintWriter out) {
            //Your code goes below
            int n = in.nextInt();
            int[] arr = new int[n];
//            int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
            for (int i = 0; i < n; i++) {
                arr[i] = in.nextInt();
//                min = Math.min(min, arr[i]);
//                max = Math.max(max, arr[i]);
            }
            int[] pref = new int[n];
            pref[0] = arr[0];
            int ans = pref[0];
            for (int i = 1; i < n; i++) {
                pref[i] = pref[i - 1] + arr[i];
                if (pref[i] % (i + 1) != 0) {
                    ans = Math.max(pref[i] / (i + 1) + 1, ans);
                } else
                    ans = Math.max(pref[i] / (i + 1), ans);
            }
            System.out.println(ans);
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
