package com.tutort.assignments.Practice;

public class GeminiSolutions {
    public static void main(String[] args) {
        String s = "abcabcbba";
        System.out.println(s);
        char[] ch = s.toCharArray();
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            count = 1;
            for (int j = i + 1; j < s.length(); j++) {
                if (ch[i] == ch[j]) {
                    ++count;
                    ch[j] = '1';
                }
            }
            if (count > 1 && ch[i] != '1') {
//                    duplicates
            }
        }
        for (int i = 0; i < ch.length; i++) {
            System.out.print(ch[i] + " ");
        }
        System.out.println();
        int c = 0, index = -1;
        for (int i = 0; i < ch.length; i++) {
            if (ch[i] == '1') {
                ++c;
                if (c == 2) {
                    index = i;
                    break;
                }
            }
        }
        System.out.println("ans: " + s.charAt(index));
    }
}
