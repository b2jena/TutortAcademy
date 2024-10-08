package com.tutort.assignments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Leetcode: https://leetcode.com/problems/minimum-absolute-difference/description/

public class MinimumAbsoluteDifference {
    public static List<List<Integer>> minimumAbsDifference(int[] arr) {
        // first need to find min abs diff
        // then make a list of list of int
        // I think I'm doing too many operations
        // O(NLogN)
        Arrays.sort(arr);
        int min = Integer.MAX_VALUE;
        int len = arr.length;
        for (int i = 1; i < len; i++) {
            min = Math.min(arr[i] - arr[i - 1], min);
        }
        System.out.println("minDiff: " + min);

        List<List<Integer>> listList = new ArrayList<>();
        for (int i = 1; i < len; i++) {
            if (arr[i] - arr[i - 1] == min) {
                listList.add(Arrays.asList(arr[i - 1], arr[i]));
            }
//            listList.removeIf(List::isEmpty);
        }
        return listList;
    }

    public static void main(String[] args) {
        int[] arr = new int[4];
        for (int i = 0; i < 4; i++) {
            arr[i] = i + 1;
        }
        // [[1, 2], [2, 3], [3, 4]]
        List<List<Integer>> output = minimumAbsDifference(arr);
        System.out.println(output);
        int[] arr1 = new int[]{3, 8, -10, 23, 19, -4, -14, 27};
        output = minimumAbsDifference(arr1);
        System.out.println(output);
        int[] arr2 = new int[]{-25, -51, -29, -23, 57, -18};
        output = minimumAbsDifference(arr2);
        System.out.println(output);
        int[] arr3 = new int[]{34, -5, -24, 60, 2, -46};
        output = minimumAbsDifference(arr3);
        System.out.println(output);
    }
}

/**
 * Example 1:
 * <p>
 * Input: arr = [4,2,1,3]
 * Output: [[1,2],[2,3],[3,4]]
 * Explanation: The minimum absolute difference is 1. List all pairs with difference equal to 1 in ascending order.
 * Example 2:
 * <p>
 * Input: arr = [1,3,6,10,15]
 * Output: [[1,3]]
 * Example 3:
 * <p>
 * Input: arr = [3,8,-10,23,19,-4,-14,27]
 * Output: [[-14,-10],[19,23],[23,27]]
 */
