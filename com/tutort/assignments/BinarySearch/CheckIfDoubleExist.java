package com.tutort.assignments.BinarySearch;

import java.util.Arrays;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

public class CheckIfDoubleExist {
//    public ArrayList<TreeNode> toArray(TreeNode root) {
//        if (root == null)
//            return null;
//
//        ArrayList<TreeNode> array = new ArrayList<TreeNode>();
//        inorder(root.getLeft());
//        array.add(root.getValue());
//        inorder(root.getRight());
//        return array;
//    }
//
//    public TreeNode increasingBST(TreeNode root) {
//        if (root == null) {
//            return root;
//        }
//        ArrayList<TreeNode> ele = toArray(root);
//        Collections.sort(ele);
//        TreeNode ans = new TreeNode();
//        while (ele.)
//    }

    public static boolean checkIfExist(int[] arr) {
        // brute force
        Arrays.sort(arr);
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (arr[j] == arr[i] * 2 || arr[i] == arr[j] * 2) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Integer i = 10;
        i++;
        System.out.println(i);
        int[] arr = {10, 2, 5, 3};
        System.out.println(checkIfExist(arr));
    }
}
