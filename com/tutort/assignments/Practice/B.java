package com.tutort.assignments.Practice;

import java.io.*;
import java.util.StringTokenizer;

public class B {
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
            // Your code goes below
            String s = in.next();
            long n = s.length();
            long ones = 0;
            for (int i = 0; i < n; i++) {
                if (s.charAt(i) == '1') {
                    ++ones;
                }
            }
            if (n == ones) {
                System.out.println(n * n);
            } else {
                long mx = Integer.MIN_VALUE, p = 0, i = 0, one = 0;
                while (true) {
                    if (s.charAt((int) (i % n)) == '1') {
                        ++one;
                    } else {
                        mx = Math.max(mx, one);
                        one = 0;
                    }
                    mx = Math.max(mx, one);
                    ++p;
                    ++i;
                    if (p >= n && s.charAt((int) (i % n)) == '0') {
                        break;
                    }
                }
                mx += 1;
                System.out.println(((mx * mx) / 4));
            }
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
