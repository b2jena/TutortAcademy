package com.tutort.assignments.Practice;

import java.util.Stack;

public class FinalPrices {
    public static int[] finalPrices(int[] prices) {
//        For the ith item in the shop with a loop find the first position j satisfying the conditions and apply the discount, otherwise, the discount is 0.
//        Input: prices = [8,4,6,2,3]
//        Output: [4,2,4,2,3]
//        Explanation:
//        For item 0 with price[0]=8 you will receive a discount equivalent to prices[1]=4, therefore, the final price you will pay is 8 - 4 = 4.
//        For item 1 with price[1]=4 you will receive a discount equivalent to prices[3]=2, therefore, the final price you will pay is 4 - 2 = 2.
//        For item 2 with price[2]=6 you will receive a discount equivalent to prices[3]=2, therefore, the final price you will pay is 6 - 2 = 4.
//        For items 3 and 4 you will not receive any discount at all.
        Stack<Integer> st = new Stack<>();
        int n = prices.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (prices[j] <= prices[i]) {
                    prices[i] -= prices[j];
                    break;
                }
            }
        }
        return prices;
    }

    public static void main(String[] args) {
        int[] prices = {8, 4, 6, 2, 3};
        int[] farr = finalPrices(prices);
        for (int i = 0; i < farr.length; i++) {
            System.out.print(farr[i] + " ");
        }
        System.out.println("\n--------------------------Bitwise operators------------------------");
        System.out.println(~0);
    }
}
