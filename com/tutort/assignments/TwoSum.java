package com.tutort.assignments;

import java.util.HashMap;

public class TwoSum {
    public static int[] twoSum(int[] nums, int target) {
        // HashMap
        HashMap<Integer, Integer> seen = new HashMap<>();
        int[] arr = new int[2];
        for (int i = 0; i < nums.length; ++i) {
            int b = nums[i], a = target - b;
            if (seen.containsKey(a)) {
                arr[0] = seen.get(a);
                arr[1] = i;
                return arr;
            }
            seen.put(b, i);
        }
        return new int[]{};
    }

    public static void main(String[] args) {

    }
}
