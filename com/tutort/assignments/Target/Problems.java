package com.tutort.assignments.Target;

import java.util.HashMap;
import java.util.Map;

/**
 * Longest Substring with At Most K Distinct Characters
 * Problem:
 * Given a string s and an integer k, return the length of the longest substring that contains at most k distinct characters.
 * <p>
 * Input: s = "eceba", k = 2
 * Output: 3
 * Explanation: The longest substring with at most 2 distinct characters is "ece".
 */
public class Problems {
    public static void main(String[] args) {
        String s = "eceba";
        int k = 2;
        String s1 = "aa";
        int k1 = 1;
        System.out.println(lengthOfLongestSubstring(s, k));
        System.out.println(lengthOfLongestSubstring(s1, k1));
    }

    public static int lengthOfLongestSubstring(String s, int k) {
        if (k == 0 || s == null || s.isEmpty()) {
            return 0;
        }
        Map<Character, Integer> map = new HashMap<>();
        int left = 0, maxLength = 0;
        for (int r = 0; r < s.length(); r++) {
            char c = s.charAt(r);
            map.put(c, map.getOrDefault(c, 0) + 1);
            while (map.size() > k) {
                char lc = s.charAt(left);
                map.put(lc, map.get(lc) - 1);
                if (map.get(lc) == 0) {
                    map.remove(lc);
                }
                left++;
            }
            maxLength = Math.max(maxLength, r - left + 1);
        }
        return maxLength;
    }
}
