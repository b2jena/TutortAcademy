package com.tutort.assignments.Pattern;

public class StarPattern {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j <= i; j++) {
                System.out.print("* ");
            }
            System.out.println();
        }
        int counter = 1;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j <= i; j++) {
                System.out.print(counter + " ");
                ++counter;
            }
            System.out.println();
        }
        for (int i = 0; i < 5; i++) {
            counter = 1;
            for (int j = 0; j <= i; j++) {
                System.out.print(counter + " ");
                ++counter;
            }
            System.out.println();
        }
    }
}
