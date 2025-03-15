package com.tutort.assignments.BinarySearch;

public class LowerUpperBound {
    public static int lowerBound(int[] arr, int key) {
        int s = 0, ans = -1;
        int e = arr.length - 1;
        while (s <= e) {
            int mid = (s + e) / 2;
            if (arr[mid] == key) {
                ans = mid;
                e = mid - 1;
            } else if (arr[mid] > key) {
                e = mid - 1;
            } else {
                s = mid + 1;
            }
        }
        return ans;
    }

    public static int upperBound(int[] arr, int key) {
        int s = 0, ans = -1;
        int e = arr.length - 1;
        while (s <= e) {
            int mid = (s + e) / 2;
            if (arr[mid] == key) {
                ans = mid;
                s = mid + 1;
            } else if (arr[mid] > key) {
                e = mid - 1;
            } else {
                s = mid + 1;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = {0, 1, 1, 2, 3, 3, 3, 3, 3, 4, 5, 5, 5, 10};
        System.out.println(arr.length);
        int freqOfKey = upperBound(arr, 3) - lowerBound(arr, 3) + 1;
        System.out.println(freqOfKey);
    }
}
