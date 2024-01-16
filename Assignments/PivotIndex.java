package Assignments;

public class PivotIndex {
    public static void main(String[] args) {
        /*Example 1:
Input: nums = [1,7,3,6,5,6]
Output: 3
Explanation:
The pivot index is 3.
Left sum = nums[0] + nums[1] + nums[2] = 1 + 7 + 3 = 11
Right sum = nums[4] + nums[5] = 5 + 6 = 11*/
        int[] nums = new int[]{1, 7, 3, 6, 5, 6};
        int[] rightSum = new int[nums.length];
        int[] leftSum = new int[nums.length];
        // leftSum is prefix sum
        leftSum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            leftSum[i] = leftSum[i - 1] + nums[i];
        }
        rightSum[nums.length - 1] = nums[nums.length - 1];
        for (int i = nums.length - 2; i >= 0; i--) {
            rightSum[i] = rightSum[i + 1] + nums[i];
        }
        for (int i = 0; i < nums.length; i++) {
            if (leftSum[i] == rightSum[i]) {
                System.out.println("Pivot element: " + i);
                return;
            }
        }
    }
}
