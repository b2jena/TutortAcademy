package com.tutort.assignments.Practice;

import java.util.ArrayList;
import java.util.List;

public class LCA1 {
    // Lists to store the path
    static List<Integer> path1;
    static List<Integer> path2;

    // Function to find the path from the root node
    // to the target node.
    static boolean findPath(Node root, int val,
                            List<Integer> path) {
        // Returnig false if we encounter a null node.
        if (root == null)
            return false;

        path.add(root.val);

        // Returning true, if we encounter the
        // target node.
        if (root.val == val) {
            return true;
        }

        // Returning true if we find the
        // target node in any of the subtree.
        if (findPath(root.left, val, path)
                || findPath(root.right, val, path)) {
            return true;
        }

        // Backtrack by removing the last element
        // of the path list.
        path.remove(path.size() - 1);

        // Returning false if nothing worked out.
        return false;
    }

    // Helper function to find the LCA
    static int findLCA(Node root, int n1, int n2) {
        // Using a function to find the path from
        // root to n1 or n2.
        if (!findPath(root, n1, path1)
                || !findPath(root, n2, path2)) {
            System.out.println("Please enter valid input");
            return -1;
        }

        int ind;
        // Now iterate over the path list found.
        for (ind = 0; ind < path1.size()
                && ind < path2.size(); ind++) {
            // If there is a mismatch break the loop.
            if (path1.get(ind) != path2.get(ind))
                break;
        }

        // Return the node encountered just before
        // the mismatch.
        return path1.get(ind - 1);
    }

    // Function to find the LCA
    static int LCA(Node root, int n1, int n2) {
        path1 = new ArrayList<>();
        path2 = new ArrayList<>();

        return findLCA(root, n1, n2);
    }

    public static void main(String[] args) {
        // Making the following tree
        //          1
        //  	    / \
        // 	   /   \
        // 	  2     3
        // 	 / \     \
        //     /   \     \
        //    4     5     6
        //     \         / \
        //      \       /   \
        //       7     8     9

        Node root = new Node(1);
        root.left = new Node(2);
        root.left.left = new Node(4);
        root.left.left.right = new Node(7);
        root.left.right = new Node(5);
        root.right = new Node(3);
        root.right.right = new Node(6);
        root.right.right.left = new Node(8);
        root.right.right.right = new Node(9);

        System.out.println("LCA of 4 and 5 is " +
                LCA(root, 4, 5));
        System.out.println("LCA of 7 and 4 is " +
                LCA(root, 7, 4));
        System.out.println("LCA of 5 and 8 is " +
                LCA(root, 5, 8));
        // Passing the wrong input
        System.out.println("LCA of 5 and 10 is " +
                LCA(root, 5, 10));
    }

    // Node class
    static class Node {
        int val;
        Node left, right;

        Node(int val) {
            this.val = val;
        }
    }

    //    public Node lowestCommonAncestor(Node root, Node p, Node q) {
//        ArrayList<Node> p1 = new ArrayList<>();
//        ArrayList<Node> p2 = new ArrayList<>();
//        // fills the path
//        if (!findPath(root, p1, p.val) || !findPath(root, p2, q.val)) {
//            return null;
//        }
//        for (int i = 0; i < p1.size() - 1 && i < p2.size() - 1; i++) {
//            if (p1.get(i + 1) != p2.get(i + 1)) {
//                return p1.get(i);
//            }
//        }
//        return null;
//    }
}
