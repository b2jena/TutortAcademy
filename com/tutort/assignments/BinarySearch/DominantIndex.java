package com.tutort.assignments.BinarySearch;

import java.util.Arrays;

public class DominantIndex {
    public static int dominantIndex(int[] nums) {
        int[] numsc = nums.clone();
        Arrays.sort(numsc);
        int n = numsc.length;
        int h = numsc[n - 1];
        int sh = numsc[n - 2];
        if (h >= 2 * sh) {
            for (int i = 0; i < n; i++) {
                if (nums[i] == h) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] nums = {3, 6, 1, 0};
        System.out.println(dominantIndex(nums));
    }
}
