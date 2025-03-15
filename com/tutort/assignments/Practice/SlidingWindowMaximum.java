package com.tutort.assignments.Practice;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class SlidingWindowMaximum {
    public static void main(String[] args) {
        int N = 9, K = 3;
        int[] arr = {1, 2, 3, 1, 4, 5, 2, 3, 6};
        ArrayList<Integer> ans = max_of_subarrays(arr, N, K);
        for (int i : ans) {
            System.out.print(i + " ");
        }
    }

    static ArrayList<Integer> max_of_subarrays(int[] arr, int n, int k) {
        // Your code here
        // brute force
        ArrayList<Integer> ans = new ArrayList<>();
//        for (int i = 0; i < n - k + 1; i++) {
//            int mx = arr[i];
//            // k-sized subarrays
//            for (int j = i + 1; j < i + k; j++) {
//                mx = Math.max(arr[j], mx);
//            }
//            ans.add(mx);
//        }
        Deque<Integer> dq = new ArrayDeque<Integer>();
        for (int i = 0; i < k; ++i) {
            while (!dq.isEmpty() && arr[i] >= arr[dq.peekLast()])
                dq.removeLast();
            dq.addLast(i);
        }
        for (int i = k; i < n; ++i) {
            // System.out.print(arr[dq.peek()] + " ");
            ans.add(arr[dq.peek()]);
            while ((!dq.isEmpty()) && dq.peek() <= i - k)
                dq.removeFirst();
            while ((!dq.isEmpty()) && arr[i] >= arr[dq.peekLast()])
                dq.removeLast();
            dq.addLast(i);
        }
        ans.add(arr[dq.peek()]);
        // System.out.print(arr[dq.peek()]);
        return ans;
    }
}

class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        // brute force approach
        ArrayList<Integer> ans = new ArrayList<>();
        int n = nums.length;
        // total number of subarrays: n - k + 1
        for (int i = 0; i < n - k + 1; i++) {
            int mx = nums[i];
            // k-sized subarrays
            for (int j = i + 1; j < i + k; j++) {
                mx = Math.max(nums[j], mx);
            }
            ans.add(mx);
        }
        int l = ans.size();
        int[] ret = new int[l];
        for (int i = 0; i < l; i++) {
            ret[i] = ans.get(i);
        }
        return ret;
    }
}