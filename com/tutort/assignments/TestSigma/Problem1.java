package com.tutort.assignments.TestSigma;

import java.util.ArrayDeque;
import java.util.Deque;

public class Problem1 {
//    ou are given a string 's', and a number 'k'.
//The task is to remove 'k' consecutive (adjacent) identical letters from the string, and repeat this process until you can't remove any more.
//The goal is to find the final string after all possible removals based on the rules mentioned.
//'s' only contains lowercase English letters.
//Example 1:
//Input: s = "deeedbbcccbdaa", k = 3
//Output: "aa"
//If you have the string "deeedbbcccbdaa" and 'k' is 3, you start by removing "eee" and "ccc," resulting in "ddbbbdaa".
//Then you remove "bbb," getting "dddaa".
//Finally, you remove "ddd," and the string becomes "aa."
//Example 2:
//Input: s = "pbbcggttciiippooaais", k = 2
//Output: "ps"
//Example 3:
//Input: s = "abcd", k = 2
//Output: "abcd"
//If you have the string "abcd" and 'k' is 2, you can't remove any characters because there are no adjacent identical letters of length 2.

    public static void main(String[] args) {
        System.out.println(removeDupli("deeedbbcccbdaa", 3));
        System.out.println(removeDupli("pbbcggttciiippooaais", 2));
    }

    private static String removeDupli(String s, int k) {
        Deque<Pair> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if (!stack.isEmpty() && stack.peek().ch == c) {
                stack.peek().count++;
                if (stack.peek().count == k) {
                    stack.pop();
                }
            } else {
                stack.push(new Pair(c, 1));
            }
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            Pair p = stack.removeLast();
            for (int i = 0; i < p.count; i++) {
                sb.append(p.ch);
            }
        }
        return sb.toString();
    }

    static class Pair {
        char ch;
        int count;

        Pair(char ch, int count) {
            this.ch = ch;
            this.count = count;
        }
    }
}
