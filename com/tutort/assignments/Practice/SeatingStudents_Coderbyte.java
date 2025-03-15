package com.tutort.assignments.Practice;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class SeatingStudents_Coderbyte {
    public static boolean isOccupied(int d, Set<Integer> od, int td) {
        return d > td || od.contains(d);
    }

    public static int ArrayChallenge(int[] arr) {
        // code goes here
        int example = Integer.parseInt("200");
        int totDesk = arr[0];
        Set<Integer> occDesk = new HashSet<>();
        for (int i = 1; i < arr.length; i++) {
            occDesk.add(arr[i]);
        }
        int count = 0;
        for (int i = 1; i < totDesk; i++) {
            if (isOccupied(i, occDesk, totDesk)) {
                continue;
            }
            if (i % 2 != 0) {
                if (!isOccupied(i + 1, occDesk, totDesk)) {
                    ++count;
                }
            }
            if (!isOccupied(i + 2, occDesk, totDesk)) {
                ++count;
            }
        }
        // String ans = count+":av078kcgtu";
        return count;
    }

    public static void main(String[] args) {
        // keep this function call here
        Scanner s = new Scanner(System.in);
//        System.out.print(ArrayChallenge(s.nextLine()) + ":av078kcgtu");
    }
}