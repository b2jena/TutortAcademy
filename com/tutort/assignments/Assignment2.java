package com.tutort.assignments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class Assignment2 {
    static Logger logger = Logger.getLogger(Assignment2.class.getName());

    public static void main(String[] args) {
        // min/max of 3 elements
        logger.info("Enter 3 numbers: ");
        Scanner in = new Scanner(System.in);
        int a = in.nextInt();
        int b = in.nextInt();
        int c = in.nextInt();
        int max = Math.max(a, Math.max(b, c));
        int min = Math.min(a, Math.min(b, c));
        logger.info("Max:" + max);
        logger.info("Min:" + min);
        // Find mid elements out of 3 elements.
        int mid = middleElement(a, b, c, max, min);
        logger.info("Mid element:" + mid);
        starPattern1();
        System.out.println("###########");
        starPattern2();
//      // Return all odd elements from array
        int[] arr = new int[]{1, 5, 6, 4, 3, 2, 8};
        int[] oddArray = oddPartOfArray(arr);
        System.out.println(Arrays.toString(oddArray));
        int sum = arraySum(arr);
        System.out.println("SUM OF ARRAY ELEMENTS: " + sum);
        int firstEven = firstEvenInArray(arr);
        System.out.println("First even number in the given array: " + firstEven);
    }

    private static int firstEvenInArray(int[] arr) {
        for (int i : arr) {
            if ((i & 1) == 0) {
                return i;
            }
        }
        return -1;
    }

    private static int arraySum(int[] arr) {
        int sum = 0;
        for (int i : arr) {
            sum += i;
        }
        return sum;
    }

    private static int[] oddPartOfArray(int[] arr) {
        List<Integer> ls = new ArrayList<>();
        for (int j : arr) {
            if (j % 2 == 1) {
                ls.add(j);
            }
        }
        int[] odd = new int[ls.size()];
        for (int i = 0; i < ls.size(); i++) {
            odd[i] = ls.get(i);
        }
        return odd;
    }

    private static void starPattern2() {
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 6 - i; j++) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
    }

    private static void starPattern1() {
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
    }

    private static int middleElement(int a, int b, int c, int max, int min) {
        int mid;
        if (a != max && a != min) {
            mid = a;
        } else if (b != max && b != min) {
            mid = b;
        } else {
            mid = c;
        }
        return mid;
    }
}
