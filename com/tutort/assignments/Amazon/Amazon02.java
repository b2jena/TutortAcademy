package com.tutort.assignments.Amazon;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;


class Result02 {

    /*
     * Complete the 'countPossibleWinners' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts INTEGER_ARRAY initialRewards as parameter.
     */

    public static int countPossibleWinners(List<Integer> initialRewards) {
        int n = initialRewards.size();
        if (n <= 1) {
            return n;
        }
        List<Integer> sortedRewards = new ArrayList<>(initialRewards);
        Collections.sort(sortedRewards);
        int max1 = sortedRewards.get(n - 1);
        int max2 = sortedRewards.get(n - 2);
        int possibleWInnersCount = 0;
        int runnerUpPrize = n - 1;
        for (int currentReward : initialRewards) {
            int winnerScore = currentReward + n;
            int maxOtherInitialReward;
            if (currentReward == max1) {
                maxOtherInitialReward = max2;
            } else {
                maxOtherInitialReward = max1;
            }
            int mcs = maxOtherInitialReward + runnerUpPrize;
            if (winnerScore >= mcs) {
                possibleWInnersCount++;
            }
        }
        return possibleWInnersCount;
    }

}

class Amazon02 {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int initialRewardsCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> initialRewards = IntStream.range(0, initialRewardsCount).mapToObj(i -> {
                    try {
                        return bufferedReader.readLine().replaceAll("\\s+$", "");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(toList());

        int result = Result02.countPossibleWinners(initialRewards);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
