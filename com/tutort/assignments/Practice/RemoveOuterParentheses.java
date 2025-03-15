package com.tutort.assignments.Practice;

public class RemoveOuterParentheses {
    public static String removeOuterParentheses(String s) {
        // resultant string
        StringBuilder res = new StringBuilder();
        // stores count of opened parentheses
        int count = 0;
        // Traverse the string
        for (char c : s.toCharArray()) {
            // if opening parentheses is encountered and their count > 0
            if (c == '(' && count++ > 0) {
                res.append(c);
            }
            // if closing parentheses is encountered and their count < count of opening parentheses
            if (c == ')' && count-- > 1) {
                res.append(c);
            }
        }
        assert res != null;
        return res.toString();
    }

    public static void main(String[] args) {
        String s = "(()())(())";
        String s1 = "(()())(())(()(()))";
        String s2 = "()()";
        System.out.println(removeOuterParentheses(s));
        System.out.println(removeOuterParentheses(s1));
        System.out.println(removeOuterParentheses(s2));

    }
}
