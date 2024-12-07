package com.tutort.assignments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FourSumII {
    public static void main(String[] args) {
        int[] arr = new int[]{1, 0, -1, 0, -2, 2};
        int target = 0;
        System.out.println(fourSum(arr, target));
        arr = new int[]{2, 2, 2, 2, 2};
        target = 8;
        System.out.println(fourSum(arr, target));
        arr = new int[]{0, 0, 0, 1000000000, 1000000000, 1000000000, 1000000000};
        target = 1000000000;
        System.out.println(fourSum(arr, target));
        arr = new int[]{0, 0, 0, -1000000000, -1000000000, -1000000000, -1000000000};
        target = -1000000000;
        System.out.println(fourSum(arr, target));
        arr = new int[]{1000000000, 1000000000, 1000000000, 1000000000};
        target = -294967296;
        System.out.println(fourSum(arr, target));
        arr = new int[]{1000000000, 1000000000, 1000000000, 999999999};
        target = -294967297;
        System.out.println(fourSum(arr, target));
    }

    public static List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        if (nums.length < 4)
            return ans;
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue; // prevents duplicate result in ans list
            for (int j = i + 1; j < nums.length - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) continue; // prevents duplicate results in ans list
                int low = j + 1;
                int high = nums.length - 1;
                while (low < high) {
                    int sum = nums[i] + nums[j] + nums[low] + nums[high];
                    if (sum <= -294967296) {
                        ++low;
                        --high;
                        break;
                    }
                    if (sum == target) {
                        System.out.println(sum);
                        ans.add(Arrays.asList(nums[i], nums[j], nums[low], nums[high]));
                        while (low < high && nums[low] == nums[low + 1]) low++; // skipping over duplicate on low
                        while (low < high && nums[high] == nums[high - 1]) high--; // skipping over duplicate on high
                        ++low;
                        --high;
                    } else if (sum < target) {
                        ++low;
                    } else {
                        --high;
                    }
                }
            }
        }
        return ans;
    }
}
