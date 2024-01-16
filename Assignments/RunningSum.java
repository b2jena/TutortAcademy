package Assignments;

public class RunningSum {
    public static void main(String[] args) {
        /*
        * Example 1:
            Input: nums = [1,2,3,4]
            Output: [1,3,6,10]
            Explanation: Running sum is obtained as follows: [1, 1+2, 1+2+3, 1+2+3+4].*/
        int[] nums = new int[]{1, 2, 3, 4};
        int[] runSum = new int[nums.length];
        runSum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            runSum[i] = runSum[i - 1] + nums[i];
        }
        System.out.println("Printing running sum of nums array:");
        for (int i = 0; i < runSum.length; i++) {
            System.out.print(runSum[i] + " ");
        }
        System.out.println();
    }
}
