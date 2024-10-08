package com.tutort.assignments;

import java.util.ArrayList;
import java.util.List;

public class PascalsTriangle {
    public static void main(String[] args) {
        int numRows = 5;
        List<List<Integer>> pt = generate(numRows);
        for (int i = 0; i < pt.size(); i++) {
            for (int j = 0; j < pt.get(i).size(); j++) {
                System.out.print(pt.get(j) + ", ");
            }
            System.out.println();
        }
    }

    public static List<List<Integer>> generate(int numRows) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> row, pre = null;
        // number of iterations = numRows
        for (int i = 0; i < numRows; ++i) {
            row = new ArrayList<>();
            // from j to i
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
}
