package com.tutort.assignments.Practice;

import java.util.Arrays;

public class CheckifTwoarraysareEqualOrNot {
    //Function to check if two arrays are equal or not.
    public static boolean check(long[] A, long[] B, int N) {
        boolean flag = false;
        Arrays.sort(A);
        Arrays.sort(B);

        if (A.length == B.length) {
            for (int i = 0; i < A.length; i++) {
                if (A[i] != B[i]) {
                    flag = true;
                    break;
                }
            }
        } else {
            return false;
        }
        return !flag;
        //Your code here
    }

    public static void main(String[] args) {
        int N = 5;
        long[] A = {3, 3};
        long[] B = {2, 2};
        System.out.println(check(A, B, N));
    }
}
