package com.tutort.assignments.Practice;

import java.io.*;
import java.util.StringTokenizer;

public class JoeyTakesMoney {
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

    //    Hint: What is the maximum value of a+b when a⋅b=x?
    static class Task {
        static final int MOD = (int) (1e9 + 7);

        public static long gcd(long a, long b) {
            return b == 0 ? a : gcd(b, a % b);
        }

        public void solve(int testNumber, InputReader in, PrintWriter out) {
            //Your code goes below
            int n = in.nextInt();
//            System.out.println(n);
            int[] arr = new int[(int) n];
            for (int i = 0; i < n; i++) {
                arr[i] = in.nextInt();
            }

//            while (true) {
//                boolean gotit = false;
//                for (int i = 0; i < n; i++) {
//                    for (int j = i + 1; j < n; j++) {
//                        long gcdv = gcd(arr[i], arr[j]);
//                        if (gcdv > 1) {
//                            gotit = true;
//                            arr[i] = gcdv;
//                            arr[j] = arr[i] * arr[j]; // gcd
//                            break;
//                        }
//                    }
//                    if (gotit) {
//                        break;
//                    }
//                }
//                if (!gotit) {
//                    break;
//                }
//            }
            // sum
            long sum = 1;
            for (int i = 0; i < n; i++) {
                sum *= arr[i];
            }
            sum += n - 1;
            sum *= 2022;
            System.out.println((sum));
        }
    }

    static class InputReader {
        public BufferedReader take;
        public StringTokenizer split;

        public InputReader(InputStream stream) {
            take = new BufferedReader(new InputStreamReader(stream), 100000000);
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
