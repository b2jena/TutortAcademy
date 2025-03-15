package com.tutort.assignments.Practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class CodejamCollectingPancakes {
    static int[][][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        for (int i = 1; i <= t; i++) {
            int n = Integer.parseInt(br.readLine());
            int[] a = new int[n];
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                a[j] = Integer.parseInt(st.nextToken());
            }
            st = new StringTokenizer(br.readLine());
            int la = Integer.parseInt(st.nextToken());
            int ra = Integer.parseInt(st.nextToken());
            int lb = Integer.parseInt(st.nextToken());
            int rb = Integer.parseInt(st.nextToken());
            dp = new int[n + 1][n + 1][n + 1];
            for (int[][] layer : dp) {
                for (int[] row : layer) {
                    Arrays.fill(row, -1);
                }
            }
            int ans = solve(a, la - 1, ra - 1, lb - 1, rb - 1, n);
            System.out.println("Case #" + i + ": " + ans);
        }
    }

    public static int solve(int[] a, int la, int ra, int lb, int rb, int n) {
        if (la > ra || lb > rb) {
            return 0;
        }
        if (dp[la][ra][lb] != -1) {
            return dp[la][ra][lb];
        }
        int ans = 0;
        // Alice takes the leftmost stack
        if (la <= ra && lb <= rb && a[la] > a[ra]) {
            ans = a[la] + solve(a, la + 2, ra, lb + 1, rb, n);
        }
        // Alice takes the rightmost stack
        if (la <= ra && lb <= rb && a[ra] >= a[la]) {
            ans = Math.max(ans, a[ra] + solve(a, la, ra - 2, lb, rb - 1, n));
        }
        // Bob takes the leftmost stack
        if (la <= ra && lb <= rb && ans == 0 && lb <= ra && a[lb] > a[rb]) {
            ans = solve(a, la + 1, ra, lb + 1, rb, n);
        }
        // Bob takes the rightmost stack
        if (la <= ra && lb <= rb && ans == 0 && lb <= ra && a[rb] >= a[lb]) {
            ans = solve(a, la, ra - 1, lb, rb - 1, n);
        }
        dp[la][ra][lb] = ans;
        return ans;
    }
}
