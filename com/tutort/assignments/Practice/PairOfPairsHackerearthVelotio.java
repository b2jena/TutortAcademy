package com.tutort.assignments.Practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class PairOfPairsHackerearthVelotio {
    public static int solve(int Q, int[][] pairs, int N, int[] A) {
        int ans = 0;
        HashMap<Integer, ArrayList<Integer>> dic = new HashMap<>();

        for (int i = 0; i < N; i++) {
            int num = A[i];
            if (!dic.containsKey(num)) {
                dic.put(num, new ArrayList<>());
            }
            dic.get(num).add(i);
        }

        for (int[] pair : pairs) {
            int u = pair[0];
            int v = pair[1];

            if (dic.containsKey(u) && dic.containsKey(v)) {
                for (int i = 0; i < dic.get(u).size(); i++) {
                    ans += bs(dic.get(v), dic.get(u).get(i));
                }
            }
        }

        return ans;
    }

    public static int bs(List<Integer> arr, int i) {
        int low = -1;
        int high = arr.size();

        while (low + 1 < high) {
            int mid = low + (high - low) / 2;

            if (arr.get(mid) <= i) {
                low = mid;
            } else {
                high = mid;
            }
        }

        return arr.size() - high;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt();

        for (int t = 0; t < T; t++) {
            int Q = scanner.nextInt();
            int[][] pairs = new int[Q][2];

            for (int i = 0; i < Q; i++) {
                pairs[i][0] = scanner.nextInt();
                pairs[i][1] = scanner.nextInt();
            }

            int N = scanner.nextInt();
            int[] A = new int[N];

            for (int i = 0; i < N; i++) {
                A[i] = scanner.nextInt();
            }

            int result = solve(Q, pairs, N, A);
            System.out.println(result);
        }
    }
}
