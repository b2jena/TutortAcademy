package com.tutort.assignments.Practice;
/*
 * Enter your code here. Read input from STDIN. Print your output to STDOUT.
 * Your class should be named CandidateCode.
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class LazyInternTechgigCodingSolution {
    static Location manager;

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        List<Location> vacant = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String row = sc.nextLine().replace(" ", "");
            if (row.contains("M")) {
                manager = new Location(i, row.indexOf('M'));
            }
            for (int j = 0; j < 8; j++) {
                if (row.charAt(j) == 'V') {
                    vacant.add(new Location(i, j));
                }
            }
        }
        int ans = n + 1;
        while (vacant.size() > 0) {
            int temp = 0;
            Location cvr = vacant.stream().min(Comparator.comparingInt(i -> Math.abs(i.row - manager.row))).get();
            if (cvr.col > 3 && manager.col > 3 && cvr.row == manager.row) {
                ans = 0;
                break;
            } else if (cvr.col < 4 && manager.col > 3) {
                temp += Math.abs(manager.row - cvr.row) + 2;
            } else if (cvr.col > 3 && manager.col < 4) {
                temp += Math.abs(cvr.row - manager.row) + 2;
            } else {
                temp += Math.abs(manager.row - cvr.row);
            }
            if (temp < ans) {
                ans = temp;
            }
            vacant.remove(cvr);
        }
        System.out.println(ans);
        //Write code here

    }

    static class Location {
        int row;
        int col;

        public Location(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

}
