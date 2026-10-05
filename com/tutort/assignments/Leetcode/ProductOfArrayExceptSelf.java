package com.tutort.assignments.Leetcode;

import java.util.Arrays;

public class ProductOfArrayExceptSelf {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 4};
        int[] nums1 = new int[]{-1, 1, 0, -3, 3};
        System.out.println(Arrays.toString(arrayProductExceptSelf(nums)));
        System.out.println(Arrays.toString(arrayProductExceptSelf(nums1)));
        System.out.println(Arrays.toString(arrayProductExceptSelfOptimised(nums)));
        System.out.println(Arrays.toString(arrayProductExceptSelfOptimised(nums1)));
    }

    public static int[] arrayProductExceptSelf(int[] nums) {
        int len = nums.length;
        int[] prodArray = new int[len];

        for (int i = 0; i < len; i++) {
            int prod = 1;
            for (int j = 0; j < len; j++) {
                if (i != j) prod *= nums[j];
            }
            prodArray[i] = prod;
        }
        return prodArray;
    }

    public static int[] arrayProductExceptSelfOptimised(int[] nums) {
        int len = nums.length;
        int[] prefix = new int[len];
        Arrays.fill(prefix, 1);
        int[] suffix = new int[len];
        Arrays.fill(suffix, 1);
        int[] prodArray = new int[len];
        for (int i = 1; i < len; i++) {
            prefix[i] = prefix[i - 1] * nums[i - 1];
        }
        for (int i = len - 2; i >= 0; i--) {
            suffix[i] = suffix[i + 1] * nums[i + 1];
        }
        for (int i = 0; i < len; i++) {
            prodArray[i] = suffix[i] * prefix[i];
        }
        return prodArray;
    }
}
