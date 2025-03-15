package com.tutort.assignments.Practice;

import java.util.Random;
import java.util.Scanner;

public class Integer2Words {
    // 4  3  8  2  3  7  7  6  4
    // |  |  |  |  |  |  |  |  |__ ones' place
    // |  |  |  |  |  |  |  |__ __ tens' place
    // |  |  |  |  |  |  |__ __ __ hundreds' place
    // |  |  |  |  |  |__ __ __ __ thousands' place
    // |  |  |  |  |__ __ __ __ __ tens thousands' place
    // |  |  |  |__ __ __ __ __ __ hundred thousands' place
    // |  |  |__ __ __ __ __ __ __ one millions' place
    // |  |__ __ __ __ __ __ __ __ ten millions' place
    // |__ __ __ __ __ __ __ __ __ hundred millions' place

    static String numtoword(long n, String s) {
        String[] one = {"", "one ", "two ", "three ",
                "four ", "five ", "six ", "seven ",
                "eight ", "nine ", "ten ", "eleven ",
                "twelve ", "thirteen ", "fourteen ", "fifteen ",
                "sixteen ", "seventeen ", "eighteen ", "nineteen "};
        String[] tens = {"", "", "twenty ", "thirty ", "forty ",
                "fifty ", "sixty ", "seventy ", "eighty ", "ninety "};
        String str = "";
        if (n > 19) {
            str += tens[(int) (n / 10)] + one[(int) (n % 10)];
        } else {
            str += one[(int) n];
        }
        if (n != 0) {
            str += s;
        }
        return str;
    }

    public static String convertToWords(long n) {
        String ans = "";
        ans += numtoword((int) (n / 10000000), "crore ");
        ans += numtoword((int) ((n / 100000) % 100), "lakh ");
        ans += numtoword((int) ((n / 1000) % 100), "thousand ");
        ans += numtoword((int) ((n / 100) % 10), "hundred ");
        if (n > 100 && n % 100 > 0) {
            ans += "and ";
        }
        ans += numtoword((int) (n % 100), "");
        return ans;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int i = 0; i < t; i++) {
            long number = sc.nextLong();
            System.out.println("In words: " + convertToWords(number));
        }
        Random random = new Random(441287210);
        for (int i = 0; i < 10; i++)
            System.out.print(random.nextInt(10) + " ");
    }
}
