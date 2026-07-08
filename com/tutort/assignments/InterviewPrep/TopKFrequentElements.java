package com.tutort.assignments.InterviewPrep;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class TopKFrequentElements {
    public static int[] topKFrequent(int[] nums, int k) {
        // for maxHeap ->         PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            freqMap.put(nums[i], freqMap.getOrDefault(nums[i], 0) + 1);
        }
        // Create a min heap that sorts entries by their frequency (value)
        // Using a min heap of size k ensures we keep the k most frequent elements
        PriorityQueue<Map.Entry<Integer, Integer>> minHeap = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));
        for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
            minHeap.add(entry);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }
        return minHeap.stream().mapToInt(Map.Entry::getKey).toArray();
    }

    public static void main(String[] args) {
        int[] nums = new int[]{11, 12, 1};
        System.out.println(topKFrequent(nums, 2));
    }
}
