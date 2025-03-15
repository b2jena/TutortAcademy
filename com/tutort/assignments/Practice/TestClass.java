package com.tutort.assignments.Practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class TestClass {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter wr = new PrintWriter(System.out);
        int T = Integer.parseInt(br.readLine().trim());
        for (int t_i = 0; t_i < T; t_i++) {
            int N = Integer.parseInt(br.readLine().trim());
            String[] arr_A = br.readLine().split(" ");
            int[] A = new int[N];
            for (int i_A = 0; i_A < arr_A.length; i_A++) {
                A[i_A] = Integer.parseInt(arr_A[i_A]);
            }

            long[] out_ = solve(N, A);
            System.out.print(out_[0]);
            for (int i_out_ = 1; i_out_ < out_.length; i_out_++) {
                System.out.print(" " + out_[i_out_]);
            }

            System.out.println();

        }
        wr.close();
        br.close();
    }

    static long[] solve(int N, int[] A) {
        // write your code here
        long[] result = new long[2];
        long score = Long.MAX_VALUE;
        long count = 0;
//        System.out.println((long) 1 << 31);
        long og = 1L << 31;
        for (long x = 0; x < og; x++) {
            long fx = 0;
            for (int a : A) {
                fx += (long) a ^ x;
                if (fx >= score) {
                    break;
                }
            }
            if (fx < score) {
                score = fx;
                count = 1;
            } else if (fx == score) {
                count++;
            }
        }
        result[0] = score;
        result[1] = count;
        return result;
    }
}