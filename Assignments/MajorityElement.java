package Assignments;

import java.util.HashMap;
import java.util.Map;

public class MajorityElement {
    public static void main(String[] args) {
        int[] nums = new int[]{2, 2, 1, 1, 1, 2, 2};
        int majorityElement = majorityElement(nums);
        System.out.println(majorityElement);
    }

    private static int majorityElement(int[] nums) {
        // this is not constant space complexity
        // Creating a HashMap object with elements of inputArray as keys and their count as values
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
        // Using for-each loop
        int key = 0, value = Integer.MIN_VALUE;
        for (Map.Entry<Integer, Integer> mapElement : elementCountMap.entrySet()) {
            if (mapElement.getValue() > value) {
                value = mapElement.getValue();
                key = mapElement.getKey();
            }
        }
        return key;
    }
}
