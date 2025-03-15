package com.tutort.assignments.Practice;

import java.util.Scanner;

public class PracticeInt {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
        int targetsum = sc.nextInt();
        // 1 2 3 7 5
        // 10 1...10 15
//        for (int i = 0; i < n; i++) {
//            int sumtrack = arr[i];
//            if (sumtrack == targetsum) {
//                System.out.println("Starting and ending index: " + i);
//            }
//            for (int j = i + 1; j < n; j++) {
//                sumtrack += arr[j];
//                if (sumtrack == targetsum) {
//                    System.out.println("Starting index: " + i);
//                    System.out.println("Ending index: " + j);
//                    return;
//                }
//            }
//        }
        int initial = 0;
        int sumtrack = arr[0];
        for (int i = 1; i <= n; i++) {
            while (initial < i && targetsum < sumtrack) {
                sumtrack -= arr[initial];
                initial += 1;
            }
            if (targetsum == sumtrack) {
                int last = i - 1;
                System.out.println("Starting index: " + initial);
                System.out.println("Ending index: " + last);
                return;
            }
            if (i < n) sumtrack += arr[i];
        }
        System.out.println("Sorry, please provide valid input");
        // O(n)
    }
}
