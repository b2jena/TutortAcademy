package com.tutort.assignments.ElivaasInterview;

import java.util.Stack;

public class QueueUsingStack {
    static Stack<Integer> s1 = new Stack<>();
    static Stack<Integer> s2 = new Stack<>();

    static void add(int a) {
        while (!s1.isEmpty()) {
            s2.push(s1.pop());
        }
        s1.push(a);
        while (!s2.isEmpty()) {
            s1.push(s2.pop());
        }
    }

    static int remove() {
        if (s1.isEmpty()) {
            return -1;
        }
        int i = s1.peek();
        s1.pop();
        return i;
    }

    public static void main(String[] args) {

    }
}

