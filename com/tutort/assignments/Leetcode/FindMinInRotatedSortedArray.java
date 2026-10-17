package com.tutort.assignments.Leetcode;

public class FindMinInRotatedSortedArray {
    public static void main(String[] args) {
        int[] nums1 = new int[]{3, 4, 5, 1, 2};
        int[] nums2 = new int[]{4, 5, 6, 7, 0, 1, 2};
        System.out.println(findMin(nums1));
        System.out.println(findMin(nums2));
    }

    public static int findMin(int[] nums) {
        int right = nums.length - 1;
        int left = 0;
        while (left < right) {
            int mid = (right + left) / 2;
            if (nums[mid] < nums[right]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return nums[left];
    }
}
