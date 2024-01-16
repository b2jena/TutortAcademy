package Assignments;

public class TwoSumForSortedArray {
    public static void main(String[] args) {
        int[] numbers = new int[]{2, 7, 11, 15};
        int target = 9;
        int[] indexes = twoSum(numbers, target);
        System.out.println("[" + indexes[0] + "] : [" + indexes[1] + "]");
    }

    private static int[] twoSum(int[] numbers, int target) {
        int l = 0, r = numbers.length - 1;
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[l] + numbers[r] == target) {
                return new int[]{l + 1, r + 1};
            } else if (numbers[l] + numbers[r] > target) {
                --r;
            } else {
                ++l;
            }
        }
        return new int[]{};
    }
}
