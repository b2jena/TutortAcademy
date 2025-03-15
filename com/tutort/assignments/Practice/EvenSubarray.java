package com.tutort.assignments.Practice;

import java.io.*;
import java.util.StringTokenizer;

public class EvenSubarray {
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

    //HINT: What are the numbers with the odd number of divisors?
    //HINT: How many numbers with the odd number of divisors can be there in the range 2⋅105?
    //HINT: Try using prefix XOR, iterating through all such numbers.
    static class Task {
        static final int MOD = (int) (1e9 + 7);

        public static long gcd(long a, long b) {
            return b == 0 ? a : gcd(b, a % b);
        }

        public void solve(int testNumber, InputReader in, PrintWriter out) {
            //Your code goes below
            int n = in.nextInt();
            int[] arr = new int[n + 1];
            for (int i = 0; i < n; i++) {
                arr[i] = in.nextInt();
            }
            long[] dp = new long[(int) (n + 1)];
            for (int i = 1; i <= n; i++) {
                dp[i] = 0L;
            }
            long[] prefix = new long[(int) (n + 1)];
            for (int i = 1; i <= n; i++) {
                prefix[i] = 0L;

            }
            long count = 0;
            for (int i = 1; i <= n; i++) {
                prefix[i] = prefix[i - 1] ^ arr[i - 1];
                for (int j = 0; j <= i; j++) {
                    int xor_val = (int) (prefix[i] ^ prefix[j]);
                    dp[xor_val] += 1;
                    if (xor_val % 2 == 0) {
                        count += dp[xor_val];
                    }
                }
            }
            dp = null;
            prefix = null;
            out.println(count);
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
