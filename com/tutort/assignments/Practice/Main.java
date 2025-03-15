package com.tutort.assignments.Practice;/* package codechef; // don't place package name! */
/**
 * author -> b2jena
 * Copyright © 2022 b2jena. All rights reserved.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/* Name of the class has to be "Main" only if the class is public. */
public class Main {
    public static void main(String[] args) throws Exception {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        Task solver = new Task();
        int tesCases = 1;
        tesCases = in.nextInt();
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

        public static List<String> findBestBlock(int[][] grid) {
            int n = grid.length;
            int maxMin = 0;
            List<String> result = new ArrayList<>();

            // iterate over each block in the grid
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    // find all neighboring blocks
                    List<int[]> neighbors = new ArrayList<>();
                    for (int x = i - 1; x <= i + 1; x++) {
                        for (int y = j - 1; y <= j + 1; y++) {
                            if (x != i || y != j) {
                                if (x >= 0 && x < n && y >= 0 && y < n) {
                                    neighbors.add(new int[]{x, y});
                                }
                            }
                        }
                    }
                    // calculate the minimum amount your friend can win
                    int minWin = Integer.MAX_VALUE;
                    for (int[] neighbor : neighbors) {
                        int x = neighbor[0];
                        int y = neighbor[1];
                        minWin = Math.min(minWin, Math.min(grid[i][j], grid[x][y]));
                    }
                    // update maxMin and result
                    if (minWin > maxMin) {
                        maxMin = minWin;
                        result.clear();
                        result.add((i + 1) + "#" + (j + 1));
                    } else if (minWin == maxMin) {
                        result.add((i + 1) + "#" + (j + 1));
                    }
                }
            }

            // return the result list
            return result;
        }

        /* Function to get no of set
            bits in binary representation
            of positive integer n */
        static int countSetBits(int n) {
            int count = 0;
            while (n > 0) {
                count += n & 1;
                n >>= 1;
            }
            return count;
        }

        public void swap(char[] strArr, int l) {
            if (l + 1 < strArr.length) {
                char tempCh = strArr[l];
                strArr[l] = strArr[l + 1];
                strArr[l + 1] = tempCh;
            }
        }

        public void solve(int testNumber, InputReader sc, PrintWriter out) throws IOException {
            int n = sc.nextInt();
            long sum = 0;
            for (int i = 1; i <= n; i++) {
                sum += Integer.bitCount(i);
            }
            System.out.println(sum);
        }
    }
}

class InputReader {
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



