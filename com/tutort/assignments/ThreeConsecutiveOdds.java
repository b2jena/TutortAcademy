package com.tutort.assignments;

public class ThreeConsecutiveOdds {
    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 34, 3, 4, 5, 7, 23, 12};
        boolean tellme = threeConsecutiveOdds(arr);
        System.out.println(tellme);
    }

    public static boolean threeConsecutiveOdds(int[] arr) {
        for (int i = 2; i < arr.length; i++) {
            if (arr[i - 2] % 2 == 1 && arr[i - 1] % 2 == 1 && arr[i] % 2 == 1) {
                return true;
            }
        }
        return false;
    }
}
