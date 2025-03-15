package com.tutort.assignments.BinarySearch;

public class FindFinalValue {

    public static boolean isPresent(int[] nums, int key) {
        for (int num : nums) {
            if (num == key) {
                return true;
            }
        }
        return false;
    }

    public static int findFinalValue(int[] nums, int original) {
        int org = original;
        while (isPresent(nums, org)) {
            org = org * 2;
        }
        return org;
    }

    public static void main(String[] args) {
        int[] nums = {5, 3, 6, 1, 12};
        System.out.println(findFinalValue(nums, 3));
    }
}
