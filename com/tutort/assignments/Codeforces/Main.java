package com.tutort.assignments.Codeforces;

import java.io.*;
import java.util.*;

/* Name of the class has to be "Main" only if the class is public. */
public class Main {
    public static void main(String[] args) throws java.lang.Exception {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        Task solver = new Task();
        int tesCases = 1;
        tesCases = in.nextInt();
        StringBuilder sb = new StringBuilder();
        for (int test = 1; test <= tesCases; test += 1) {
            solver.solve(test, in, out, sb);
        }
        System.out.println(sb + " ");
        out.close();
    }

    static class Task {
        static final int MOD = (int) (1e9 + 7);

        public static long gcd(long a, long b) {
            return b == 0 ? a : gcd(b, a % b);
        }

        // Function to return LCM of two numbers
        public static int findLCM(int a, int b) {
            int greater = Math.max(a, b);
            int smallest = Math.min(a, b);
            for (int i = greater; ; i += greater) {
                if (i % smallest == 0)
                    return i;
            }
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

        private static int findMaxSubarrayLength(int[] a, int[] b) {
            int n = a.length;
            int[] c = new int[2 * n];
            int maxLength = 0;
            Map<Integer, Integer> countMap = new HashMap<>();

            for (int i = 0; i < n; i++) {
                c[2 * i] = a[i];
                c[2 * i + 1] = b[i];
            }

            for (int i = 0; i < 2 * n; i++) {
                int element = c[i];
                int count = countMap.getOrDefault(element, 0) + 1;
                countMap.put(element, count);
                maxLength = Math.max(maxLength, count);
            }
            return maxLength;
        }

        public void swap(char[] strArr, int l) {
            if (l + 1 < strArr.length) {
                char tempCh = strArr[l];
                strArr[l] = strArr[l + 1];
                strArr[l + 1] = tempCh;
            }
        }

        public void solve(int testNumber, InputReader sc, PrintWriter out, StringBuilder sb) throws IOException {
            // TODO SOLUTION
            int n = sc.nextInt();
//            StringBuilder sb = new StringBuilder("");

            int maxlen = sc.nextInt() - 1;
            int power = sc.nextInt();
            int ans = 0;

            char[] ch = sc.next().toCharArray();
            int cur = maxlen;

            for (int i = 0; i < ch.length; i++) {
//                System.out.print(cur + " ");
                if (ch[i] == '0') cur--;
                else cur = maxlen;

                if (cur < 0) {
                    int k = i;
                    ans++;
                    for (; i < Math.min(ch.length, k + power); i++) {
                        ch[i] = '1';
                    }
                    i--;
                    cur = maxlen;
                }
            }
//            System.out.println(Arrays.toString(ch));
            sb.append(ans + "\n");
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
