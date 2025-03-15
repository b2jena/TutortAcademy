package com.tutort.assignments.Practice;

import java.io.*;
import java.util.StringTokenizer;

public class LetterCount1_CoderByte_Zeeve {
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

        public static String SearchingChallenge(String str) {
            // code goes here
            String[] arr = str.split(" ");
            int count = 0;
            String res = "-1";
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr[i].length(); j++) {
                    int cc = 0;
                    for (int k = j + 1; k < arr[i].length(); k++) {
                        if (arr[i].charAt(j) == arr[i].charAt(k)) {
                            ++cc;
                        }
                    }
                    if (cc > count) {
                        count = cc;
                        res = arr[i];
                    }
                }
            }
            StringBuilder resrev = new StringBuilder();
            resrev.append(res);
            resrev.reverse();
            return resrev + ":av078kcgtu";
        }

        public static long gcd(long a, long b) {
            return b == 0 ? a : gcd(b, a % b);
        }

        public void solve(int testNumber, InputReader in, PrintWriter out) {
            String s = in.next();
            System.out.println(SearchingChallenge(s));
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
