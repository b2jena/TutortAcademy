package com.tutort.assignments.Practice;

public class FirstCircularTour {
    public static void main(String[] args) {
        int[] Petrol = {4, 6, 7, 4};
        int[] Distance = {6, 5, 3, 5};
        System.out.println(tour(Petrol, Distance));
    }

    //Function to find starting point where the truck can start to get through
    //the complete circle without exhausting its petrol in between.
    static int tour(int[] petrol, int[] distance) {
        // Your code here
        // brute force -> O(n^2)
        int n = petrol.length;
//        for (int s = 0; s < n; s++) {
//            int curr_petrol = 0;
//            int end = s;
//            while (true) {
//                curr_petrol += (petrol[end] - distance[end]);
//                if (curr_petrol < 0) {
//                    break;
//                }
//                end = (end + 1) % n;
//                if (end == s) {
//                    return s + 1;
//                }
//            }
//        }
        // efficient approach
        int start = 0, curr_petrol = 0, prev_petrol = 0;
        for (int i = 0; i < n; i++) {
            curr_petrol += (petrol[i] - distance[i]);
            if (curr_petrol < 0) {
                start = i + 1;
                prev_petrol += curr_petrol;
                curr_petrol = 0;
            }
        }
        if (curr_petrol + prev_petrol >= 0) {
            return start + 1;
        }
        return -1;
    }
}
