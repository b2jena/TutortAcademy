package com.tutort.assignments;

import java.util.Stack;

public class MakeTheStringGreat {
    public static void main(String[] args) {
        String s = "leEeetcode";
        // output = leetcode
        String s1 = "abBAcC";
        // output = ""
        String s2 = "Pp";
        System.out.println(makeGood(s));
        System.out.println(makeGood(s1));
        System.out.println(makeGood(s2));
    }

    public static String makeGood(String s) {
        Stack<Character> stack = new Stack<>();
        stack.push(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            if (!stack.isEmpty() && (Math.abs(stack.peek() - s.charAt(i)) == 32)) {
                System.out.println("comparing " + stack.peek() + " with " + s.charAt(i));
                stack.pop();
            } else {
                stack.push(s.charAt(i));
            }
        }
        System.out.println(stack);
        System.out.println(stack);
        StringBuilder sb = new StringBuilder();
        stack.stream().forEach(c -> sb.append(c)); // iterates from bottom to top of stack
        System.out.println(sb);
        return sb.toString();
    }
}
