package com.tutort.assignments;

public class ContainerWithMostWater {
    public static int maxArea(int[] height) {
        int n = height.length;
        int marea = Integer.MIN_VALUE;
        int l = 0;
        int r = n - 1;
        int area = 0;
        while (l < r) {
            if (height[r] > height[l]) {
                area = height[l] * (r - l);
                ++l;
            } else {
                area = height[r] * (r - l);
                --r;
            }
            marea = Math.max(area, marea);
        }
        return marea;
    }

    public static void main(String[] args) {
        int[] height = new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7};
        System.out.println(maxArea(height));
    }
}
