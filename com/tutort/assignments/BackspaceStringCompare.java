package com.tutort.assignments;

import java.util.Stack;

public class BackspaceStringCompare {
    public static void main(String[] args) {
        String s = "ab#c", t = "ad#c";
        String s1 = "ab##", t1 = "c#d#";
        String s2 = "a#c", t2 = "b";
        String s3 = "a##c", t3 = "#a#c";
        System.out.println(backspaceCompare(s, t));
        System.out.println(backspaceCompare(s1, t1));
        System.out.println(backspaceCompare(s2, t2));
        System.out.println(backspaceCompare(s3, t3));
    }

    private static boolean backspaceCompare(String s, String t) {
        Stack<Character> stacks = new Stack<>();
        Stack<Character> stackt = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '#') {
                if (!stacks.isEmpty()) {
                    stacks.pop();
                }
            } else {
                stacks.push(c);
            }
        }
        for (char c : t.toCharArray()) {
            if (c == '#') {
                if (!stackt.isEmpty()) {
                    stackt.pop();
                }
            } else {
                stackt.push(c);
            }
        }
        StringBuilder sbs = new StringBuilder();
        stacks.stream().forEach(c -> sbs.append(c));
        StringBuilder sbt = new StringBuilder();
        stackt.stream().forEach(c -> sbt.append(c));
        System.out.println(sbs + " " + sbt);
        return sbs.toString().contentEquals(sbt);
    }
}
