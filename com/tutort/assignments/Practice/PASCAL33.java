package com.tutort.assignments.Practice;

import java.io.*;
import java.util.StringTokenizer;

public class PASCAL33 {
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

        public static long gcd(long a, long b) {
            return b == 0 ? a : gcd(b, a % b);
        }

        // Function to print first
        // n lines of Pascal's Triangle
        static void printPascal(int n) {

            // Iterate through every line
            // and print entries in it
            for (int line = 0; line < n; line++) {
                // Every line has number of
                // integers equal to line number
                for (int i = 0; i <= line; i++)
                    System.out.print(binomialCoeff
                            (line, i) + " ");

                System.out.println();
            }
        }

        // Link for details of this function
        // https://www.geeksforgeeks.org/space-and-time-efficient-binomial-coefficient/
        static int binomialCoeff(int n, int k) {
            int res = 1;

            if (k > n - k)
                k = n - k;

            for (int i = 0; i < k; ++i) {
                res *= (n - i);
                res /= (i + 1);
            }
            return res;
        }

        public void solve(int testNumber, InputReader in, PrintWriter out) {
            //Your code goes below
            int n = in.nextInt();
            printPascal(n);
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
