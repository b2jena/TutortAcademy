package com.tutort.assignments.Practice;

public class AddDigits {
    public static int sumDigits(int n) {
        if (n == 0)
            return 0;
        return (n % 10) + sumDigits(n / 10);
    }

    public static int addDigits(int num) {
        while (num / 10 > 0) {
            num = sumDigits(num);
        }
        return num;
//        if (num == 0) return 0;
//        else if (num % 9 == 0) return 9;
//        else return num % 9;
    }

    public static void main(String[] args) {
        int num = 38;
        System.out.println(addDigits(num));
    }
}
