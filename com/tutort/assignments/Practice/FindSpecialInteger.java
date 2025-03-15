package com.tutort.assignments.Practice;

import java.util.HashMap;
import java.util.Map;

public class FindSpecialInteger {
    public static int findSpecialInteger(int[] arr) {
        Map<Integer, Integer> mp = new HashMap<>();
        int count = 0;
        for (int it : arr) {
            count = mp.getOrDefault(it, 0);
            mp.put(it, count + 1);
        }
        for (Map.Entry m : mp.entrySet()) {
            if ((int) m.getValue() > (arr.length / 4)) {
                return (int) m.getKey();
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int n = 9;
        int[] arr = new int[]{1, 2, 2, 6, 6, 6, 6, 7, 10};
        System.out.println(findSpecialInteger(arr));
    }
}
