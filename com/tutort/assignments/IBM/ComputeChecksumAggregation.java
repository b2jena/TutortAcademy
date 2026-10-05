package com.tutort.assignments.IBM;

class computeChecksumAggregationResult {

    public static int computeChecksumAggregation(int n) {
        long MOD = 1000000007;
        long totalSum = 0;

        // C(i, j) = (i % j) + (j % i)
        // Since (i % j) + (j % i) summed over all 1 <= i, j <= n can be decoupled:
        // Sum of (i % j) for all i, j from 1 to n is symmetric to sum of (j % i).
        // Therefore, we can compute sum of (i % j) for all 1 <= i, j <= n and multiply by 2.

        long sumMod = 0;

        // Sum of (i % j) = Sum of (i - floor(i/j) * j)
        for (int j = 1; j <= n; j++) {
            // For a fixed j, as i goes from 1 to n:
            // We sum (i - floor(i/j) * j)
            long count = n;

            // Sum of i from 1 to n is n * (n + 1) / 2
            long sumI = ((long) n * (n + 1) / 2) % MOD;

            // Sum of floor(i / j) * j can be broken down into ranges where floor(i / j) is constant.
            long term2 = 0;
            for (int k = 1; k * j <= n; k++) {
                int l = k * j;
                int r = Math.min(n, (k + 1) * j - 1);
                long numElements = (r - l + 1);

                // floor(i / j) is equal to k for all i in [l, r]
                term2 = (term2 + (k * j) % MOD * numElements) % MOD;
            }

            long currentJSum = (sumI - term2 + MOD) % MOD;
            sumMod = (sumMod + currentJSum) % MOD;
        }

        // Since C(i, j) = (i % j) + (j % i), and the matrix of pairs is symmetric,
        // total sum is 2 * sum of (i % j) over all i, j.
        totalSum = (2 * sumMod) % MOD;

        return (int) totalSum;
    }
}

public class ComputeChecksumAggregation {
    public static void main(String[] args) {
        System.out.println(computeChecksumAggregationResult.computeChecksumAggregation(100));
    }
}
