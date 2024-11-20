package com.tutort.assignments;

import java.util.Arrays;

public class SpiralMatrixII {
    public static int[][] generateMatrix(int n) {
        int[][] arr = new int[n][n];
        int[][] dir = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int currDir = 0;
        // current row and current column
        int currR = 0, currC = 0;
        int i = 0;
        while (i < n * n) {

        }
        return arr;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.deepToString(generateMatrix(5)));
    }
}
