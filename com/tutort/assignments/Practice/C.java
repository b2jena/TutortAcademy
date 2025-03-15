package com.tutort.assignments.Practice;

import java.io.*;
import java.util.StringTokenizer;

public class C {
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
            int n = in.nextInt();
            int[][] arr = new int[n][n - 1];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n - 1; j++) {
                    arr[i][j] = in.nextInt();
                }
            }
            int a = 0, b = 0;
            for (int i = 0; i < n; i++) {
                boolean flag = false;
                for (int j = 0; j < n; j++) {
                    if (i == j) {
                        continue;
                    }
                    if (arr[i][1] == arr[j][0]) {
                        a = i;
                        b = j;
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    break;
                }
            }
            System.out.print(arr[a][0] + " ");
            for (int i = 0; i < n - 1; i++) {
                System.out.print(arr[b][i] + " ");
            }
            System.out.println();
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
