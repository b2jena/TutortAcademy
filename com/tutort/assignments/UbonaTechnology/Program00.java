package com.tutort.assignments.UbonaTechnology;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Problem: Rotting Oranges (with 8-direction adjacency)
 * <p>
 * Grid legend:
 * 0 -> empty cell
 * 1 -> fresh orange
 * 2 -> rotten orange
 * <p>
 * Idea:
 * Use multi-source BFS starting from all initially rotten oranges (value 2).
 * Each BFS layer represents one minute. In each minute, every rotten orange
 * will rot all adjacent fresh oranges. Here adjacency includes 8 directions
 * (up/down/left/right and the 4 diagonals). Count minutes (layers) until no
 * fresh oranges remain or the queue exhausts.
 */
public class Program00 {
    public static void main(String[] args) {
        // Sample run. Grid values: 0 - empty, 1 - fresh, 2 - rotten
        Program00 solution = new Program00();
        int[][] grid1 = {{2, 1, 1}, {0, 1, 1}, {1, 1, 0}};
        System.out.println(solution.minRottingTime(grid1));
    }

    /**
     * Returns the minimum number of minutes required so that no fresh orange remains.
     * If there are no fresh oranges to begin with, returns 0.
     * <p>
     * Time complexity: O(R*C) where R and C are grid dimensions.
     * Space complexity: O(R*C) for the BFS queue in the worst case.
     */
    public int minRottingTime(int[][] grid) {
        // Basic bounds
        int rows = grid.length, cols = grid[0].length;

        // Queue holds coordinates [r, c] of rotten oranges for BFS (multi-source)
        Queue<int[]> queue = new LinkedList<>();

        // Count of fresh oranges remaining
        int fresh = 0;

        // Initialize: count fresh oranges and enqueue all initially rotten ones
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == 1) {
                    ++fresh; // track total fresh oranges
                } else if (grid[r][c] == 2) {
                    queue.offer(new int[]{r, c}); // starting points for BFS
                }
            }
        }

        // No fresh oranges from the start -> 0 minutes needed
        if (fresh == 0) {
            return 0;
        }

        // Minutes elapsed (number of BFS layers processed)
        int minutes = 0;

        // 8-directional neighbors: N, E, S, W, and diagonals NE, NW, SE, SW
        int[][] directions = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

        // BFS over time: process level by level; each level equals one minute
        while (!queue.isEmpty() && fresh > 0) {
            int qs = queue.size(); // number of cells to process in this minute
            for (int i = 0; i < qs; i++) {
                int[] present = queue.poll(); // current rotten orange position
                for (int[] d : directions) {
                    int nr = present[0] + d[0];
                    int nc = present[1] + d[1];

                    // If neighbor is inside bounds and is fresh, rot it and enqueue
                    if (nr < rows && nr >= 0 && nc < cols && nc >= 0 && grid[nr][nc] == 1) {
                        grid[nr][nc] = 2; // becomes rotten this minute
                        --fresh;           // one fewer fresh orange remaining
                        queue.offer(new int[]{nr, nc});
                    }
                }
            }
            ++minutes; // completed one minute (BFS layer)
        }

        // If fresh > 0 here, it means some fresh oranges were unreachable from any rotten source.
        // As written, this returns minutes even if fresh > 0. If you need to indicate impossibility,
        // you might return -1 when fresh > 0. Keeping existing behavior per original code.
        return minutes;
    }
}
