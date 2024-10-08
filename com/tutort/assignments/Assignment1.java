package com.tutort.assignments;

public class Assignment1 {

    // 1. Minimum of three numbers
    public static int minimumOfThree(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }

    // 2. Check if a couple is eligible for marriage
    public static String isEligibleForMarriage(int girlAge, int boyAge) {
        return (girlAge >= 18 && boyAge >= 21) ? "Yes" : "No";
    }

    // 3. Print tax amount using ternary operator
    public static double calculateTax(double billAmount) {
        return billAmount > 50000 ? billAmount * 0.10 : billAmount * 0.05;
    }

    // 4. Check if a year is a leap year
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    // 5. Check if a number is odd or even
    public static String isOddOrEven(int number) {
        return (number % 2 == 0) ? "Even" : "Odd";
    }

    public static void main(String[] args) {
        // Test the methods
        System.out.println("Minimum of 3, 7, 5: " + minimumOfThree(3, 7, 5));
        System.out.println("Couple eligibility (girl: 19, boy: 22): " + isEligibleForMarriage(19, 22));
        System.out.println("Tax on bill amount 60000: " + calculateTax(60000));
        System.out.println("Year 2024 is leap year: " + isLeapYear(2024));
        System.out.println("Number 7 is: " + isOddOrEven(7));
    }
}

