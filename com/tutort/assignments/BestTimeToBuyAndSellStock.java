package com.tutort.assignments;

public class BestTimeToBuyAndSellStock {
    public static void main(String[] args) {
        int[] prices = new int[]{7, 1, 5, 3, 6, 4};
        int maxProfit = maxProfit(prices);
        System.out.println(maxProfit);
    }

    private static int maxProfit(int[] prices) {
        // mn = minimum value of prices
        int mn = Integer.MAX_VALUE;
        for (int price : prices) {
            if (price < mn) {
                mn = price;
            }
        }
        int maxProfit = 0, currProfit = 0, currMin = Integer.MAX_VALUE;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < currMin) {
                currMin = prices[i];
            }
            currProfit = prices[i] - currMin;
            if (maxProfit < currProfit) {
                maxProfit = currProfit;
            }
        }
        return maxProfit;
    }
}
