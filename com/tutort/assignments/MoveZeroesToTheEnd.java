package com.tutort.assignments;

import java.util.Arrays;

public class MoveZeroesToTheEnd {
    public static void main(String[] args) {
        int[] nums = new int[]{0, 1, 0, 3, 12};
        int zeroes = 0;
        for (int num : nums) {
            if (num == 0) {
                ++zeroes;
            }
        }
        int nonzeroes = nums.length - zeroes;
        int[] nonzero = new int[nonzeroes];
        int c = 0;
        for (int num : nums) {
            if (num != 0) {
                nonzero[c] = num;
                ++c;
            }
        }
        int[] ans = new int[nums.length];
        for (int i = 0; i < ans.length; i++) {
            if (i < c) {
                ans[i] = nonzero[i];
            } else {
                ans[i] = 0;
            }
        }
        System.arraycopy(ans, 0, nums, 0, nums.length);
        System.out.println(Arrays.toString(ans));
    }
}
