package com.tutort.assignments.Practice;/* package codechef; // don't place package name! */
/**
 * author -> b2jena
 * Copyright © 2022 b2jena. All rights reserved.
 */

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/* Name of the class has to be "Main" only if the class is public. */
public class SelectThreeSticks {
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
            //Your code goes below
            int n = in.nextInt();
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = in.nextInt();
            }
            Arrays.sort(arr);
            int aa = Integer.MAX_VALUE;
            for (int i = 2; i < n; ++i) {
                aa = Math.min(arr[i] - arr[i - 2], aa);
            }
            out.println(aa);
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
//    int minimizeMemory(vector<int> processes, int m) {
//        int tot=0;
//        int n=processes.size();
//        for(int i=0;i<n;i++){
//            tot+=processes[i];
//        }
//        int res=0;
//        for(int i=0;i<m;i++){
//            res+=processes[i];
//        }
//        int csum=res;
//        for(int i=m;i<n;i++){
//            csum+=(processes[i]-processes[i-m]);
//            res=max(res,csum);
//        }
//        return tot-res;
//    }
//def getMinimumCost(parcels, k):
//        # Write your code here
//        pc=len(parcels);
//        if pc==k:
//        return 0;
//        pi={}
//        for i in parcels:
//        pi[i]=1;
//        fc=0
//        i=1
//        while i<=k and pc!=k:
//        if i not in pi:
//        pi[i]=1
//        fc+=i
//        pc+=1
//        i+=1
//        return fc;