package com.tutort.assignments;

import java.util.HashMap;
import java.util.Map;

public class ContainsDuplicate {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 1};
        boolean containsDuplicate = containsDuplicate(nums);
        System.out.println(containsDuplicate);
    }

    public static boolean containsDuplicate(int[] nums) {
        HashMap<Integer, Integer> elementCountMap = new HashMap<>();
        // checking every element of the inputArray
        for (int i : nums) {
            if (elementCountMap.containsKey(i)) {
                // If element is present in elementCountMap, incrementing its count by 1
                elementCountMap.put(i, elementCountMap.get(i) + 1);
            } else {
                // If element is not present in elementCountMap,
                // adding this element to elementCountMap with 1 as it's value
                elementCountMap.put(i, 1);
            }
        }
        // Using for-each loop
        for (Map.Entry<Integer, Integer> mapElement : elementCountMap.entrySet()) {
            if (mapElement.getValue() > 1) {
                return true;
            }
        }
        return false;
    }
}
