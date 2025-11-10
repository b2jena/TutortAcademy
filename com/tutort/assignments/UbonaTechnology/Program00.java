package com.tutort.assignments.UbonaTechnology;

import java.util.LinkedList;
import java.util.Queue;

public class Program00 {
    public static void main(String[] args) {
        // 0 - empty, 1 - fresh, 2 - rotten
        Program00 solution = new Program00();
        int[][] grid1 = {{2, 1, 1}, {0, 1, 1}, {1, 1, 0}};
        System.out.println(solution.minRottingTime(grid1));
    }

    public int minRottingTime(int[][] grid) {
        int rows = grid.length, cols = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        int fresh = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == 1) {
                    ++fresh;
                } else if (grid[r][c] == 2) {
                    queue.offer(new int[]{r, c});
                }
            }
        }
        if (fresh == 0) {
            return 0;
        }
        int minutes = 0;
        int[][] directions = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        while (!queue.isEmpty() && fresh > 0) {
            int qs = queue.size();
            for (int i = 0; i < qs; i++) {
                int[] present = queue.poll();
                for (int[] d : directions) {
                    int nr = present[0] + d[0];
                    int nc = present[1] + d[1];
                    if (nr < rows && nr >= 0 && nc < cols && nc >= 0 && grid[nr][nc] == 1) {
                        grid[nr][nc] = 2;
                        --fresh;
                        queue.offer(new int[]{nr, nc});
                    }
                }
            }
            ++minutes;
        }
        return minutes;
    }
}
