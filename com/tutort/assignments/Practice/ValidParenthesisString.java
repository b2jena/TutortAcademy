package com.tutort.assignments.Practice;

public class ValidParenthesisString {
    public static int maxDepth(String s) {
        int res = 0, curr = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                ++curr;
                res = Math.max(res, curr);
            }
            if (c == ')') {
                --curr;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        String s = "(1+(2*3)+((8)/4))+1";
        String s1 = "(1)+((2))+(((3)))";
        System.out.println(maxDepth(s1));
    }
}
