package Assignments;

public class LeftRightSumDifference {
    public static void main(String[] args) {
        // TC: O(n), SC: O(n)
        /*Example 1:
Input: nums = [10,4,8,3]
Output: [15,1,11,22]
Explanation: The array leftSum is [0,10,14,22] and the array rightSum is [15,11,3,0].
The array answer is [|0 - 15|,|10 - 11|,|14 - 3|,|22 - 0|] = [15,1,11,22].*/
        int[] nums = new int[]{10, 4, 8, 3};
        int[] rightSum = new int[nums.length];
        int[] leftSum = new int[nums.length];
        // leftSum is prefix sum
        leftSum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            leftSum[i] = leftSum[i - 1] + nums[i];
        }
        for (int i = 0; i < nums.length; i++) {
            leftSum[i] -= nums[i];
        }
        rightSum[nums.length - 1] = nums[nums.length - 1];
        for (int i = nums.length - 2; i >= 0; i--) {
            rightSum[i] = rightSum[i + 1] + nums[i];
        }
        for (int i = nums.length - 1; i >= 0; i--) {
            rightSum[i] -= nums[i];
        }
        int[] diff = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            diff[i] = Math.abs(leftSum[i] - rightSum[i]);
        }
        for (int j : diff) {
            System.out.print(j + " ");
        }
        System.out.println();
    }
}
