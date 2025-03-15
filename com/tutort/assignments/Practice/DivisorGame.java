package com.tutort.assignments.Practice;

public class DivisorGame {
    public static boolean divisorGame(int n) {
        // If the current number is even, we can always subtract a 1 to make it odd. If the current number is odd, we must subtract an odd number to make it even.
        // clearly if n is even -> alice wins else alice loses
        // dp approach
        if (n == 1) {
            return false;
        }
        boolean[] dp = new boolean[1001];
        dp[0] = false;
        dp[1] = false;
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j < i; j++) {
                if (i % j == 0) {
                    if (!dp[i - j]) {
                        dp[i] = true;
                        break;
                    }
                }
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        int n = 999;
        System.out.println(divisorGame(n));
    }
}
