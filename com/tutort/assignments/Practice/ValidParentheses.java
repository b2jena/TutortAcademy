package com.tutort.assignments.Practice;

import java.util.Stack;

public class ValidParentheses {
    public static boolean isValid(String s) {
        Stack<Character> stack = new Stack<Character>();
        // Iterate through string until empty
        for (char i : s.toCharArray()) {
            // Push any open parentheses onto stack
            if (i == '(' || i == '[' || i == '{')
                stack.push(i);
                // Check stack for corresponding closing parentheses, false if not valid
            else if (i == ')' && !stack.empty() && stack.peek() == '(')
                stack.pop();
            else if (i == ']' && !stack.empty() && stack.peek() == '[')
                stack.pop();
            else if (i == '}' && !stack.empty() && stack.peek() == '{')
                stack.pop();
            else
                return false;
        }
        // return true if no open parentheses left in stack
        return stack.isEmpty();
//        Stack<Character> stack = new Stack<Character>();
//        for (char c : s.toCharArray()) {
//            if (c == '(') {
//                stack.push(')');
//                System.out.println(stack);
//            } else if (c == '{') {
//                stack.push('}');
//                System.out.println(stack);
//            } else if (c == '[') {
//                stack.push(']');
//                System.out.println(stack);
//            } else if (stack.isEmpty() || stack.pop() != c) {
//                System.out.println(stack);
//                return false;
//            }
//        }
//        if (stack.isEmpty()) {
//            return true;
//        } else {
//            return false;
//        }
    }

    public static void main(String[] args) {
        String s = "()[]{}";
        System.out.println(isValid(s));
    }
}
