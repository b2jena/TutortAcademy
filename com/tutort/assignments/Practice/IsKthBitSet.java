package com.tutort.assignments.Practice;

public class IsKthBitSet {
    public static void isKthBitSet(int n, int k) {
        if ((n & (1 << (k - 1))) > 0)
            System.out.print("SET");
        else
            System.out.print("NOT SET");
    }

    public static void main(String[] args) {
        int n = 5, k = 1;

        // Function call
        isKthBitSet(n, k);
    }
}
