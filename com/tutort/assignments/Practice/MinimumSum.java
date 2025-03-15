package com.tutort.assignments.Practice;

import java.util.Arrays;

public class MinimumSum {
    public static int minimumSum(int num) {
        int[] dig = new int[4]; // For each digit
        int cur = 0;
        while (num > 0) // Getting each digit
        {
            dig[cur++] = num % 10;
            num /= 10;
        }
        Arrays.sort(dig); // Ascending order
        int num1 = dig[0] * 10 + dig[2]; // 1st and 3rd digit
        int num2 = dig[1] * 10 + dig[3]; // 2nd and 4th digit
        return num1 + num2;
    }

    public static void main(String[] args) {
        int num = 2932;
        System.out.println(minimumSum(num));
    }
}
