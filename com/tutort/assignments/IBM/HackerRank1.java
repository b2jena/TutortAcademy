package com.tutort.assignments.IBM;

import java.io.*;

class HackerRank1Result {

    /*
     * Complete the 'computeChecksumAggregation' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts INTEGER n as parameter.
     */

    public static int computeChecksumAggregation(int n) {
        // Write your code here
        long MOD = 1000000007;
        long totalSum = 0;
        long sum = 0;
        for (int i = 1; i <= n; i++) {
            long count = n;
            long sum1 = ((long) n * (n + 1) / 2) % MOD;
            long t2 = 0;
            for (int j = 1; j * i <= n; j++) {
                int l = j * i;
                int r = Math.min(n, (j + 1) * i - 1);
                long elementCount = (r - l + 1);
                t2 = (t2 + (j * i) % MOD * elementCount) % MOD;
                // System.out.print(t2);
            }
            long curSum = (sum1 - t2 + MOD) % MOD;
            // System.out.print(" curSum "+ curSum);
            sum = (sum + curSum) % MOD;
        }
        totalSum = (2 * sum) % MOD;
        // System.out.println(totalSum);
        return (int) totalSum;
    }

}

public class HackerRank1 {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        int result = HackerRank1Result.computeChecksumAggregation(n);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

