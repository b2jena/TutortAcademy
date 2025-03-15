package com.tutort.assignments.Practice;

import java.util.ArrayList;
import java.util.List;

public class PascalTriangle {
    public static List<List<Integer>> generate(int numRows) {
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        List<Integer> row, pre = null;
        for (int i = 0; i < numRows; ++i) {
            row = new ArrayList<Integer>();
            for (int j = 0; j <= i; ++j)
                if (j == 0 || j == i)
                    row.add(1);
                else {
                    assert pre != null;
                    row.add(pre.get(j - 1) + pre.get(j));
                }
            pre = row;
            ans.add(row);
        }
        return ans;
    }

    public static void main(String[] args) {
        int numRows = 10;
        List<List<Integer>> ans = generate(numRows);
        for (List<Integer> list : ans) {
            for (Integer it : list) {
                System.out.print(it + " ");
            }
            System.out.println();
        }
    }
}
