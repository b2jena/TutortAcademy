package com.tutort.assignments;

import java.util.Stack;
// https://www.geeksforgeeks.org/stack-class-in-java/
public class StackPlay {
    public static void main(String[] args) {
        Stack<Integer> integerStack = new Stack<>();
        Stack<Character> characterStack = new Stack<>();
        integerStack.push(1);
        integerStack.push(2);
        integerStack.push(3);
        System.out.println("stack now: " + integerStack);
        integerStack.pop();
        System.out.println("now :" + integerStack);
        System.out.println("checking topmost element using peek :" + integerStack.peek());
        System.out.println(integerStack.pop());

        // Create a new stack
        Stack<Integer> s = new Stack<>();

        // Push elements onto the stack
        s.push(1);
        s.push(2);
        s.push(3);
        s.push(4);

        // Pop elements from the stack
        while(!s.isEmpty()) {
            System.out.println(s.pop());
        }
    }
}
