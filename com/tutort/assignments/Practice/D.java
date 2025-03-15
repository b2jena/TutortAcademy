package com.tutort.assignments.Practice;

import java.io.*;
import java.util.StringTokenizer;

public class D {
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

        public void solve(int testNumber, InputReader in, PrintWriter out) {
            // Your code goes below
            String s = "abcabcbba";
            char[] ch = s.toCharArray();
            int count = 0;
            for (int i = 0; i < s.length(); i++) {
                count = 1;
                for (int j = i + 1; j < s.length(); i++) {
                    ++count;
                    ch[j] = '1';
                }
                if (count > 1 && ch[i] != '1') {
//                    duplicates
                }
            }
            for (int i = 0; i < ch.length; i++) {
                System.out.print(ch[i] + " ");
            }
            int c = 0, index = -1;
            for (int i = 0; i < ch.length; i++) {
                if (ch[i] == '1') {
                    ++c;
                    if (c == 2) {
                        index = i;
                        break;
                    }
                }
            }
            System.out.println(s.charAt(index));
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
