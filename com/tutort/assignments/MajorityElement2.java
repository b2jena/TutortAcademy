package com.tutort.assignments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MajorityElement2 {
    public static void main(String[] args) {
        int[] nums = new int[]{2, 2, 1, 1, 1, 2, 2};
        List<Integer> majorityElement = majorityElement(nums);
        System.out.println(majorityElement);
    }

    private static List<Integer> majorityElement(int[] nums) {
        HashMap<Integer, Integer> elementCountMap = new HashMap<>();
        // checking every element of the inputArray
        for (int i : nums) {
            if (elementCountMap.containsKey(i)) {
                // If element is present in elementCountMap, incrementing it's count by 1
                elementCountMap.put(i, elementCountMap.get(i) + 1);
            } else {
                // If element is not present in elementCountMap,
                // adding this element to elementCountMap with 1 as it's value
                elementCountMap.put(i, 1);
            }
        }
        List<Integer> majorityElements = new ArrayList<>();
        // Using for-each loop
        for (Map.Entry<Integer, Integer> mapElement : elementCountMap.entrySet()) {
            if (mapElement.getValue() > (nums.length / 3)) {
                majorityElements.add(mapElement.getKey());
            }
        }
        return majorityElements;
    }
}
