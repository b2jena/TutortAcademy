package Assignments;

public class Merge2SortedArrays {
    public static void main(String[] args) {
        int[] nums1 = new int[]{1, 2, 3, 0, 0, 0};
        int m = 3;
        int n = 3;
        int[] nums2 = new int[]{2, 5, 6};
        merge(nums1, m, nums2, n);
        for (int j : nums1) {
            System.out.print(j + " ");
        }
        System.out.println();
    }

    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1, j = n - 1, k = m + n - 1;
        while (i >= 0 && j >= 0) {
            if (nums1[i] < nums2[j]) {
                nums1[k] = nums2[j];
                --k;
                --j;
            } else {
                nums1[k] = nums1[i];
                --k;
                --i;
            }
        }
        while (j >= 0) {
            nums1[k] = nums2[j];
            --k;
            --j;
        }
    }
}
