package com.tutort.assignments.Practice;

public class OpenTextIncreaseDigit {
    public static int increaseDigits(int n, int k) {
        // Extract the digits from n
        int digit_1 = n / 100;
        int digit_2 = (n / 10) % 10;
        int digit_3 = n % 10;

        // Calculate the maximum possible increment for each digit
        int max_increment_1 = Math.min(9 - digit_1, k);
        int max_increment_2 = Math.min(9 - digit_2, k - max_increment_1);
        int max_increment_3 = Math.min(9 - digit_3, k - max_increment_1 - max_increment_2);

        // Construct the maximum possible three-digit value
        int max_value = (digit_1 + max_increment_1) * 100 + (digit_2 + max_increment_2) * 10 + (digit_3 + max_increment_3);

        return max_value;
    }

    public static void main(String[] args) {
        int n = 456;
        int k = 3;
        int result = increaseDigits(n, k);
        System.out.println(result);  // Output: 789
    }
}
