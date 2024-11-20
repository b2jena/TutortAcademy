package com.tutort.assignments;

import java.util.ArrayList;
import java.util.List;

public class SpiralMatrix {
    public static List<Integer> spiralOrder(int[][] matrix) {
        // m * n matrix
        List<Integer> ans = new ArrayList<>();
        int m = matrix.length, n = matrix[0].length;
        int[][] dir = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int currDir = 0;
        // current row and current column
        int currR = 0, currC = 0;
        // loop until collect all elements, and when we reach the end - STOP
        while (ans.size() < m * n) {
            ans.add(matrix[currR][currC]);
            matrix[currR][currC] = 1001;  // mark visited
            int nextR = currR + dir[currDir][0];
            int nextC = currC + dir[currDir][1];
            // change direction when encounter invalid cases
            if (nextR < 0 || nextR >= m || nextC < 0 || nextC >= n || matrix[nextR][nextC] == 1001)
                currDir = (currDir + 1) % 4;
            currR += dir[currDir][0];
            currC += dir[currDir][1];
        }
        return ans;
    }

    public static void main(String[] args) {
        int[][] matrix = new int[3][3];
        matrix = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        System.out.println(spiralOrder(matrix));
    }
}
