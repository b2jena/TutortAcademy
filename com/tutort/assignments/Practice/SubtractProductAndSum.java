package com.tutort.assignments.Practice;

public class SubtractProductAndSum {
    public static int product(int n) {
        if (n == 0) {
            return 0;
        }
        int p = 1;
        while (n > 0) {
            p *= (n % 10);
            n /= 10;
        }
        return p;
    }

    public static int sum(int n) {
        int sum = 0;
        if (n == 0) {
            return 0;
        }
        while (n > 0) {
            sum += n % 10;
            n /= 10;
        }

        return sum;
    }

    public static int subtractProductAndSum(int n) {
        return product(n) - sum(n);
    }

    public static void main(String[] args) {
        int n = 4421;
        System.out.println(subtractProductAndSum(n));
    }
}
