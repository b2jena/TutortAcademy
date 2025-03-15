package com.tutort.assignments.Practice;

public class RakutenExample {
    public static boolean alphaCheck(String s) {
        int count = (int) s.toLowerCase().chars().filter(x -> x >= 'a' && x <= 'z').distinct().count();
        return count == 26;
    }

    public static void main(String[] args) throws ArithmeticException {
        // input string
        // if this string contains all the possible alphabets
        String s = "qwertyuiopasdfghjklzxc2623173663nm";
        if (alphaCheck(s)) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
        try {
            int ex = 3 / 0;
        } finally {
            System.out.println("in the end");
        }
    }
}
