package com.tutort.assignments;

import java.util.Stack;

public class RemoveAllAdjacentDuplicatesInString {
    public static void main(String[] args) {
        String s1 = "abbaca";
        System.out.println(s1.hashCode());
        System.out.println(removeDuplicates(s1));
        String s2 = "azxxzy";
        System.out.println(s2.hashCode());
        System.out.println(removeDuplicates(s2));
    }

    private static String removeDuplicates(String s) {
        Stack<Character> st = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (st.isEmpty()) {
                st.push(s.charAt(i));
            } else {
                if (st.peek() == s.charAt(i)) {
                    st.pop();
                } else {
                    st.push(s.charAt(i));
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        while (!st.isEmpty()) {
            sb.append(st.peek());
            st.pop();
        }
        System.out.println(st + " " + sb);
        return sb.reverse().toString();
    }
}
