package com.tutort.assignments.Practice;

public class JavaBasics {
    public static int sum(int a, int b) {
        int s = a + b;
        return s;
    }

    public static String concat(String a, String b) {
        return a + b + "hello World!";
    }

    public static void vsum(int a, int b) {
        int sub = a - b;
        System.out.println(sub);
    }

    public static void main(String[] args) {
//        System.out.println(sum(3, 2));
        int su = sum(3, 2); // su = 5
        String ans = concat("java", "11"); // ans = java11hello World!
        vsum(10, 6);
        System.out.println(su + " : " + ans);
    }
}
// readability
// java 8 -> after java 7
// java 11 -> lts
// primitive -> int, float, char, boolean, long
// non-primitive -> String


