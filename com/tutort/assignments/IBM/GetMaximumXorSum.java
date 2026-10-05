package com.tutort.assignments.IBM;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Result {

    public static int getMaximumXorSum(List<Integer> arr1, List<Integer> arr2) {
        long MOD = 1000000007;
        int n = arr1.size();
        long totalSum = 0;

        // Iterate through each bit position (0 to 30)
        for (int k = 0; k <= 30; k++) {
            long cnt1 = 0;
            for (Integer x : arr1) {
                // Check if the k-th bit is set (avoiding boolean/int confusion)
                if (((x >> k) & 1) != 0) {
                    cnt1++;
                }
            }
            long cnt0 = n - cnt1;

            long cnt2 = 0;
            for (Integer x : arr2) {
                if (((x >> k) & 1) != 0) {
                    cnt2++;
                }
            }
            long cnt3 = n - cnt2;

            // Total number of times the k-th bit is 1 in the matrix
            long totalOnes = (cnt0 * cnt2 + cnt1 * cnt3) % MOD;

            // Contribution of the k-th bit: totalOnes * (2^k % MOD)
            long powerOfTwo = power(2, k, MOD);
            long contribution = (totalOnes * powerOfTwo) % MOD;

            totalSum = (totalSum + contribution) % MOD;
        }

        return (int) totalSum;
    }

    // Helper function for modular exponentiation
    private static long power(long base, long exp, long mod) {
        long res = 1;
        base %= mod;
        while (exp > 0) {
            if ((exp & 1) == 1) res = (res * base) % mod;
            base = (base * base) % mod;
            exp >>= 1;
        }
        return res;
    }
}

public class GetMaximumXorSum {

    public static void main(String[] args) {
        List<Integer> arr1 = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        List<Integer> arr2 = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        System.out.println(Result.getMaximumXorSum(arr1, arr2));
    }
}
