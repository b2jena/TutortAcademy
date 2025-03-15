package com.tutort.assignments.Practice;

import java.io.*;
import java.util.StringTokenizer;

public class ARMSTRONGNUM {
    public static void main(String[] args) throws Exception {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out;
        out = new PrintWriter(outputStream);
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

        /* Function to calculate x raised to the
       power y */
        int power(int x, long y) {
            if (y == 0)
                return 1;
            if (y % 2 == 0)
                return power(x, y / 2) * power(x, y / 2);
            return x * power(x, y / 2) * power(x, y / 2);
        }

        /* Function to calculate order of the number */
        int order(int x) {
            int n = 0;
            while (x != 0) {
                n++;
                x = x / 10;
            }
            return n;
        }

        // Function to check whether the given number is
        // Armstrong number or not
        boolean isArmstrong(int x) {
            // Calling order function
            int n = order(x);
            int temp = x, sum = 0;
            while (temp != 0) {
                int r = temp % 10;
                sum = sum + power(r, n);
                temp = temp / 10;
            }

            // If satisfies Armstrong condition
            return (sum == x);
        }

        public void solve(int testNumber, InputReader in, PrintWriter out) {
            //Your code goes below
            int n = in.nextInt();
            if (isArmstrong(n)) {
                System.out.println("Yes");
            } else {
                System.out.println("No");
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
