package com.tutort.assignments.Practice;

import java.io.*;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

class CardPacketsMorganStanleyCodingRound {
    /*
     * Complete the 'cardPackets' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts INTEGER_ARRAY cardTypes as parameter.
     */

    public static int cardPackets(List<Integer> cardTypes) {
        // Write your code here
        int ans = 0;
        for (int i = 0; i < cardTypes.size(); i++) {
            if (cardTypes.get(i) % 2 != 0) {
                ++ans;
            }
        }
        if (ans < 2 || ans == 3) {
            ans = 0;
        }
        return ans;
    }
}

public class CardPacketsMorganStanleyCodingRoundSolution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int cardTypesCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> cardTypes = IntStream.range(0, cardTypesCount).mapToObj(i -> {
                    try {
                        return bufferedReader.readLine().replaceAll("\\s+$", "");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(toList());

        int result = CardPacketsMorganStanleyCodingRound.cardPackets(cardTypes);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
