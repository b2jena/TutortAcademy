package com.tutort.assignments.Practice;

public class ExceptionClassExample {
    public static void main(String[] args) {
        try {
            System.out.println(5 / 0);
        } catch (NullPointerException e) {
            System.out.println("line 6");
        } catch (ArithmeticException e) {
            System.out.println("line 8");
        } finally {
            System.out.println("at finally");
        }
    }
}
