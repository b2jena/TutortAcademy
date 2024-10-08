package com.tutort.assignments;

import java.util.ArrayList;
import java.util.List;

public class PascalsTriangle2 {
    public static void main(String[] args) {
        int numRows = 3;
        List<Integer> pt2 = generate(numRows);
        for (Integer integer : pt2) {
            System.out.print(integer + " ");
        }
    }

    public static List<Integer> generate(int numRows) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> row, pre = null;
        // number of iterations = numRows
        for (int i = 0; i < numRows + 1; ++i) {
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
        return ans.get(numRows);
    }
}
