package com.tutort.assignments;

public class Max3ElementsArray {
    public static void main(String[] args) {
        int[] arr = {12, 13, 1, 10, 34, 1};
        int n = arr.length;
        print3largest(arr, n);
    }

    private static void print3largest(int[] arr, int n) {
        /* There should be atleast three elements */
        if (n < 3) {
            System.out.print(" Invalid Input. I need atleast 3 inputs.");
            return;
        }
        int i, first, second, third;
        first = second = third = Integer.MIN_VALUE;
        // so we need to do one traversal O(n)
        for (i = 0; i < n; i++) {
            /* If current element is greater than
            first*/
            if (arr[i] > first) {
                third = second;
                second = first;
                first = arr[i];
            }

            /* If arr[i] is in between first and
            second then update second  */
            else if (arr[i] > second) {
                third = second;
                second = arr[i];
            } else if (arr[i] > third)
                third = arr[i];
        }
        System.out.println(first + " " + second + " " + third);
    }
}
