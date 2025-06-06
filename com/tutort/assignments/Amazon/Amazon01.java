package com.tutort.assignments.Amazon;

import java.io.*;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;


class Result01 {

    /*
     * Complete the 'minimizeSystemCost' function below.
     *
     * The function is expected to return a LONG_INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER k
     *  2. INTEGER_ARRAY machines
     */

    public static long minimizeSystemCost(int k, List<Integer> machines) {
        // Write your code here
        int n = machines.size();
        long totalCost = 0;
        for (int i = 0; i < n - 1; i++) {
            totalCost += Math.abs(machines.get(i) - machines.get(i + 1));
        }
        long[] internalDiffs = new long[n - 1];
        for (int i = 0; i < n - 1; i++) {
            internalDiffs[i] = Math.abs(machines.get(i) - machines.get(i + 1));
        }
        long windowSum = 0;
        for (int i = 0; i < k - 1; i++) {
            windowSum += internalDiffs[i];
        }
        long maxSaved = Long.MIN_VALUE;
        for (int i = 0; i <= n - k; i++) {
            if (i > 0) {
                windowSum -= (internalDiffs[i - 1] - internalDiffs[i + k - 2]);
            }
            long saved = windowSum;
            if (i > 0) {
                saved += internalDiffs[i - 1];
            }
            if (i + k < n) {
                saved += internalDiffs[i + k - 1];
            }
            if (i > 0 && i + k < n) {
                saved -= Math.abs(machines.get(i - 1) - machines.get(i + k));
            }
            maxSaved = Math.max(maxSaved, saved);
        }
        return totalCost - maxSaved;
    }

}

public class Amazon01 {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int k = Integer.parseInt(bufferedReader.readLine().trim());

        int machinesCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> machines = IntStream.range(0, machinesCount).mapToObj(i -> {
                    try {
                        return bufferedReader.readLine().replaceAll("\\s+$", "");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(toList());

        long result = Result01.minimizeSystemCost(k, machines);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

