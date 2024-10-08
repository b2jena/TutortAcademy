package com.tutort.assignments;

public class RichestCustomerWealth {
    public static void main(String[] args) {
        int[][] accounts = new int[][]{{1, 5}, {7, 3}, {3, 5}};
        int maxWealth = maximumWealth(accounts);
        System.out.println(maxWealth);
    }

    public static int maximumWealth(int[][] accounts) {
        int max = Integer.MIN_VALUE;
        // row traversal
        for (int[] account : accounts) {
            // column traversal
            int wealthSum = 0;
            for (int j = 0; j < accounts[0].length; j++) {
                wealthSum += account[j];
            }
            max = Math.max(wealthSum, max);
        }
        return max;
    }
}
