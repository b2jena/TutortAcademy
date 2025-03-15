package com.tutort.assignments.Practice;

import java.io.*;
import java.util.StringTokenizer;

public class STAR {
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

        // Prints diamond pattern
        // with 2n rows
        static void printDiamond(int n) {
            int space = n - 1;

            // run loop (parent loop)
            // till number of rows
            for (int i = 0; i < 2 * n; i++) {
                if (i % 2 == 0) {
                    // loop for initially space,
                    // before star printing
                    for (int j = 0; j < space; j++)
                        System.out.print(" ");

                    // Print i+1 stars
                    if (i == n + 1) {
                        for (int j = 0; j <= i; j++)
                            System.out.print("* ");
                        System.out.println();
                    }
                    for (int j = 0; j <= i; j++)
                        System.out.print("* ");

                    System.out.print("\n");
                    space--;
                }
            }

            // Repeat again in
            // reverse order
            space = 0;

            // run loop (parent loop)
            // till number of rows
            for (int i = n; i >= 0; i--) {
                if (i % 2 == 1) {
                    // loop for initially space,
                    // before star printing
                    for (int j = 0; j < space; j++)
                        System.out.print(" ");
                    // Print i stars
                    for (int j = 0; j < i; j++)
                        System.out.print(" *");

                    System.out.print("\n");
                    space++;
                }
            }
        }

        public void solve(int testNumber, InputReader in, PrintWriter out) {
            //Your code goes below
            int n = in.nextInt();
            printDiamond(n);
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
