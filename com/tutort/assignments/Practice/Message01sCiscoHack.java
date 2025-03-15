package com.tutort.assignments.Practice;

import java.io.*;
import java.util.StringTokenizer;

public class Message01sCiscoHack {
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

        public static int findMinimumFlips(String msg) {
            int minZeros = Integer.MAX_VALUE;
            int currentZeros = 0;

            for (int i = 0; i < msg.length(); i++) {
                if (msg.charAt(i) == '0') {
                    currentZeros++;
                } else {
                    if (currentZeros > 0 && currentZeros < minZeros) {
                        minZeros = currentZeros;
                    }
                    currentZeros = 0;
                }
            }

            if (currentZeros > 0 && currentZeros < minZeros) {
                minZeros = currentZeros;
            }

            if (minZeros == Integer.MAX_VALUE) {
                return 0;
            }

            return minZeros;
        }

        public int minMovesToMakePalindrome(String s) {
            int len = s.length();
            char[] strArr = s.toCharArray();
            int steps = 0;
            int l = 0, r = len - 1;                                           // use two pointers l for left and r for right.

            while (l < r) {
                if (strArr[l] == strArr[r]) {                                 // Both characters are equal. so keep going futher.
                    l++;
                    r--;
                } else {                                                      // Both characters are not equal.
                    int k = r;
                    k = findKthIndexMatchingwithLthIndex(strArr, l, k);     // loop through k, until char at index k = char at index l

                    if (k == l) {                                             // we did not find any char at k = char at index l
                        swap(strArr, l);
                        steps++;
                    } else {
                        while (k < r) {
                            swap(strArr, k);
                            steps++;
                            k++;
                        }
                        l++;
                        r--;
                    }
                }// end of else

            }   // end of while
//            System.out.println("palindrome: " + String.valueOf(strArr));
            return steps;

        }

        public int findKthIndexMatchingwithLthIndex(char[] strArr, int l, int k) {
            while (k > l) {
                if (strArr[k] == strArr[l]) {
                    return k;
                }
                k--;
            }
            return k;
        }

        public void swap(char[] strArr, int l) {
            if (l + 1 < strArr.length) {
                char tempCh = strArr[l];
                strArr[l] = strArr[l + 1];
                strArr[l + 1] = tempCh;
            }
        }

        public void solve(int testNumber, InputReader in, PrintWriter out) {
            int n = in.nextInt();
            int[] arr = new int[n];
            int[] brr = new int[n];
            int amax = Integer.MIN_VALUE;
            int bmax = Integer.MIN_VALUE;
            for (int i = 0; i < n; i++) {
                arr[i] = in.nextInt();
                if (amax < arr[i]) {
                    amax = arr[i];
                }
            }
            for (int i = 0; i < n; i++) {
                brr[i] = in.nextInt();
                if (bmax < brr[i]) {
                    bmax = brr[i];
                }
            }
            for (int i = 0; i < n; i++) {
                if (arr[i] < brr[i]) {
                    int temp = arr[i];
                    arr[i] = brr[i];
                    brr[i] = temp;
                }
            }
            if (arr[n - 1] == amax && brr[n - 1] == bmax) {
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
