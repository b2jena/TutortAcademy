package com.tutort.assignments.Leetcode;

import java.util.Arrays;
import java.util.HashMap;

public class TwoSum {
    public static void main(String[] args) {
        int[] nums = new int[]{2, 7, 11, 15};
        int target = 9;
        Solution solution = new Solution();
        System.out.println(Arrays.toString(solution.twoSum(nums, target)));
        int[] nums1 = new int[]{3, 2, 4};
        int target1 = 6;
        System.out.println(Arrays.toString(solution.twoSum(nums1, target1)));
    }
}

class Solution {
    public int[] twoSum(int[] nums, int target) {
        // HashMap Approach
        HashMap<Integer, Integer> seen = new HashMap<>();
        // key = nums[i], value = i
        int[] arr = new int[2];
        // achieve in 1 iteration O(n)
        for (int i = 0; i < nums.length; i++) {
            int a = nums[i], b = target - a;
            // on average, storing and retrieving elements from the HashMap takes constant O(1) time
            if (seen.containsKey(b)) {
                // if we get a match
                arr[0] = seen.get(b); // value or the index of b (basically b + a = target)
                arr[1] = i;
                return arr;
            }
            seen.put(a, i);
        }
        return new int[]{};
    }
}
