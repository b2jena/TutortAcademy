package com.tutort.assignments.Practice;

import java.util.*;

import static java.lang.Math.max;

public class LeetcodeSolution {
    static final int EMPTY = -1;
    static Node prev = null;
    static int index = 0;

    public int nullNodeCount(TreeNode root) {
        if (root == null)
            return 0;
        else if (root.left == null && root.right != null && (root.right.right == null && root.left.left == null)) {
            return 1 + nullNodeCount(root.right);
        } else if (root.left != null && root.right == null && (root.right.right == null && root.left.left == null)) {
            return 1 + nullNodeCount(root.left);
        } else {
            return nullNodeCount(root.left) + nullNodeCount(root.right);
        }
    }

    public int widthOfBinaryTree(TreeNode root) {
        if (root == null)
            return 0;
        Queue<TreeNode> q = new LinkedList<>();
        ((LinkedList<TreeNode>) q).push(root);
        int res = 0;
        int nullNode = nullNodeCount(root);
        while (!q.isEmpty()) {
            int count = q.size();
            res = max(res, count);
            for (int i = 0; i < count; i++) {
                TreeNode curr = q.poll();
                if (curr.left != null) {
                    q.add(curr.left);
                }
                if (curr.right != null) {
                    q.add(curr.right);
                }
            }
        }
        return res + nullNode;
    }

    //Function to convert binary tree to doubly linked list and return it.
    public Node bToDLL(Node root) {
        if (root == null) {
            return root;
        }
        Node head = bToDLL(root.left);
        if (prev == null) {
            head = root;
        } else {
            root.left = prev;
            prev.right = root;
        }
        prev = root;
        bToDLL(root.right);
        return head;
        //  Your code here
    }

    ArrayList<Integer> findSpiral(Node root) {
        ArrayList<Integer> arr = new ArrayList<Integer>();
        // Your code here
        if (root == null) {
            return arr;
        }
        Queue<Node> q = new LinkedList<Node>();
        Stack<Integer> s = new Stack<Integer>();
        boolean reverse = false;
        q.add(root);
        while (!q.isEmpty()) {
            int count = q.size();
            for (int i = 0; i < count; i++) {
                Node curr = q.poll();
                if (reverse) {
                    s.push(curr.data);
                } else {
                    arr.add(curr.data);
                }
                if (curr.left != null) {
                    q.add(curr.left);
                }
                if (curr.right != null) {
                    q.add(curr.right);
                }
            }
            if (reverse) {
                while (!s.isEmpty()) {
                    arr.add(s.pop());
                }
            }
            reverse = !reverse;
        }
        return arr;
    }

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> ll = new ArrayList<List<Integer>>();
        List<Integer> arr = new ArrayList<Integer>();
        // Your code here
        if (root == null) {
            return ll;
        }
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        Stack<Integer> s = new Stack<Integer>();
        boolean reverse = false;
        q.add(root);
        while (!q.isEmpty()) {
            int count = q.size();
            List<Integer> zig = new ArrayList<Integer>();
            for (int i = 0; i < count; i++) {
                TreeNode curr = q.poll();
                if (reverse) {
                    s.push(curr.val);
                } else {
                    zig.add(curr.val);
                    System.out.println(curr.val);
                }
                if (curr.left != null) {
                    q.add(curr.left);
                }
                if (curr.right != null) {
                    q.add(curr.right);
                }
            }
            if (zig.size() != 0)
                ll.add(zig);
            if (reverse) {
                List<Integer> zag = new ArrayList<Integer>();
                while (!s.isEmpty()) {
                    System.out.println("reverse part: " + s.peek());
                    zag.add(s.pop());
                }
                ll.add(zag);
            }
            reverse = !reverse;
        }
        return ll;
    }

    public int height(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            int i = 1 + Math.max(height(root.left), height(root.right));
            return i;
        }
    }

    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int d1 = 1 + height(root.left) + height(root.right);
        int d2 = diameterOfBinaryTree(root.left);
        int d3 = diameterOfBinaryTree(root.right);
        return Math.max(d1, Math.max(d2, d3));
    }

    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + countNodes(root.left) + countNodes(root.right);
    }

    //Function to serialize a tree and return a list containing nodes of tree.
    public void serialize(Node root, ArrayList<Integer> A) {
        //code here
        if (root == null) {
            A.add(EMPTY);
        }
        A.add(root.data);
        serialize(root.left, A);
        serialize(root.right, A);
    }

    //Function to deserialize a list and construct the tree.
    public Node deSerialize(ArrayList<Integer> A) {
        if (index == A.size())
            return null;
        int val = A.get(index);
        index++;
        if (val == EMPTY) return null;
        Node root = new Node(val);
        root.left = deSerialize(A);
        root.right = deSerialize(A);
        return root;
        //code here
    }
}