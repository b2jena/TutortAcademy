package com.tutort.assignments.IBM;

import java.io.*;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;


class HackerRank2Result {

    /*
     * Complete the 'maximumXorSum' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER_ARRAY arr1
     *  2. INTEGER_ARRAY arr2
     */

    public static int maximumXorSum(List<Integer> arr1, List<Integer> arr2) {
        // Write your code here
        long MOD = 1000000007;
        int n = arr1.size();
        long totalSum = 0;
        //each bit position
        for (long i = 0; i <= 30; i++) {
            long cnt1 = 0;
            for (Integer x : arr1) {
                if (((x >> i) & 1) == 1) {
                    // System.out.println(cnt1);
                    ++cnt1;
                }
            }
            long cnt0 = n - cnt1;
            long cnt2 = 0;
            for (Integer x : arr2) {
                if (((x >> i) & 1) == 1) {
                    ++cnt2;
                }
            }
            long cnt3 = n - cnt2;
            long total1s = (cnt0 * cnt2 + cnt1 * cnt3) % MOD;

            long powerOf2 = power(2, i, MOD);
            long contr = (powerOf2 * total1s) % MOD;
            // System.out.println(contr);
            totalSum = (totalSum + contr) % MOD;
        }
        return (int) totalSum;
    }

    private static long power(long i, long i2, long mOD) {
        long res = 1;
        i %= mOD;
        while (i2 > 0) {
            if ((i2 & 1) == 1) res = (res * i) % mOD;
            i = (int) ((i * i) % mOD);
            i2 >>= 1;
        }
        return res;
    }

}

public class HackerRank2 {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int arr1Count = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> arr1 = IntStream.range(0, arr1Count).mapToObj(i -> {
                    try {
                        return bufferedReader.readLine().replaceAll("\\s+$", "");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(toList());

        int arr2Count = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> arr2 = IntStream.range(0, arr2Count).mapToObj(i -> {
                    try {
                        return bufferedReader.readLine().replaceAll("\\s+$", "");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(toList());

        int result = HackerRank2Result.maximumXorSum(arr1, arr2);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

