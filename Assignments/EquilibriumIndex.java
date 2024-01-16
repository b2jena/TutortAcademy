package Assignments;

public class EquilibriumIndex {
    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 0, 3};
        int n = 4;
        int equilibriumIndex = findEquilibrium(arr, n);
        System.out.println(equilibriumIndex);
    }

    public static int findEquilibrium(int[] arr, int n) {
        //add code here.
        int leftSum = 0;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += arr[i];
        }
        for (int i = 0; i < n; i++) {
            sum -= arr[i];
            if (sum == leftSum) {
                return i;
            }
            leftSum += arr[i];
        }
        return -1;
    }
}
