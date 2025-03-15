package com.tutort.assignments.Practice;

import java.util.Arrays;
import java.util.Scanner;

public class SHOP_LIST {
    public static void main(String[] args) throws Exception {
        // your code goes here
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        for (int i = 0; i < n; i++) {
            int a = s.nextInt(), b = s.nextInt();
            int[] arr1 = new int[a], arr2 = new int[b];
            for (int j = 0; j < a; j++) {
                arr1[j] = s.nextInt();
            }
            for (int k = 0; k < b; k++) {
                arr2[k] = s.nextInt();
            }
            Arrays.sort(arr1);
            Arrays.sort(arr2);
            int j = 0, k = 0;
            boolean t = false;
            while (j < a & k < b) {
                t = false;
                if (b > a) break;
                if (arr2[k] == arr1[j]) {
                    t = true;
                    k++;
                    j++;
                    if (j == a & k != b) {
                        t = false;
                        break;
                    }
                    continue;
                }
                if (arr2[k] > arr1[j]) {
                    j++;
                    if (j == a) {
                        break;
                    }
                }
                if (arr2[k] < arr1[j]) break;
            }
            if (!t) System.out.println("NO");
            else System.out.println("YES");
        }
    }
}
