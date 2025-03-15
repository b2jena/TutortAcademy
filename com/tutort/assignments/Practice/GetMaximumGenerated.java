package com.tutort.assignments.Practice;

public class GetMaximumGenerated {
    public static int getMaximumGenerated(int n) {
        if (n == 0) {
            return 0;
        }
//        nums[0] = 0
//        nums[1] = 1
//        nums[2 * i] = nums[i] when 2 <= 2 * i <= n
//        nums[2 * i + 1] = nums[i] + nums[i + 1] when 2 <= 2 * i + 1 <= n
        int[] nums = new int[n + 1];
        nums[0] = 0;
        nums[1] = 1;
        for (int i = 1; i * 2 + 1 <= n; i++) {
            nums[i * 2] = nums[i];
            nums[i * 2 + 1] = nums[i] + nums[i + 1];
        }
        int mx = Integer.MIN_VALUE;
        for (int i = 0; i <= n; i++) {
            if (mx < nums[i]) {
                mx = nums[i];
            }
        }
        return mx;
    }

    public static void main(String[] args) {
        int n = 7;
        System.out.println(getMaximumGenerated(n));
    }
}
