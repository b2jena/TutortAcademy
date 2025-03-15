package com.tutort.assignments.Practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class WorkloadHackerearthVelotio {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter wr = new PrintWriter(System.out);
        int N = Integer.parseInt(br.readLine().trim());
        String[] arr_work = br.readLine().split(" ");
        int[] work = new int[N];
        for (int i_work = 0; i_work < arr_work.length; i_work++) {
            work[i_work] = Integer.parseInt(arr_work[i_work]);
        }

        long out_ = min_time(N, work);
        System.out.println(out_);

        wr.close();
        br.close();
    }

    static long min_time(int N, int[] work) {
        // Write your code here
        long result = 0;
        int l = 0, h = Integer.MAX_VALUE;
        while (l <= h) {
            int mid = l + (h - l) / 2;
            long pa1 = possibleAns(work, mid);
            long pa2 = possibleAns(work, mid + 1);
            result = Math.min(pa1, pa2);
            if (pa1 < pa2) {
                h = mid - 1;
            } else {
                l = mid + 2;
            }
        }
        return result;
    }

    static long possibleAns(int[] arr, int x) {
        long pa = 0;
        for (int i = 0; i < arr.length; i++) {
            pa += Math.abs(arr[i] - i - x + 1);
        }
        return pa;
    }
}
