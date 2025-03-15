package com.tutort.assignments.Practice;

import java.util.Scanner;

public class CodeForIndependence {
    public static String toBinaryString(int n) {
        return String.format("%32s", Integer.toBinaryString(n))
                .replaceAll(" ", "0");
    }

    // Function to swap adjacent bits of a given number
    public static int swapAdjacentBits(int n) {
        return (((n & 0xAAAAAAAA) >> 1) | ((n & 0x55555555) << 1));
    }

    public static void main(String[] args) {
//        int a = 2;
//        int b = 4;
//        int c = a & b;
//        System.out.println(c);
//        int var1 = 5;
//        int var2 = 6;
//        if ((var2 = 1) == var1) {
//            System.out.println(var2);
//        } else {
//            System.out.println(var2 + 1);
//        }
        CodeForIndependence obj = new CodeForIndependence();
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int i = 1; i <= t; i++) {
            int in = sc.nextInt();
            System.out.println(swapAdjacentBits(in));
        }
    }

    public int getSum(int a, int b) {
        return a + b;
    }
}
