package com.mycompany.binarytreeslab4;

import java.util.*;

class Node {
    int data;
    Node left, right;

    Node(int value) {
        data = value;
        left = right = null;
    }
}

class BinaryTree {
    Node root;
    
    BinaryTree() {
        root = null;
    }
    
    BinaryTree(int value) {
        root = new Node(value);
    }
    
    List<Integer> preOrder() {
        List<Integer> res = new ArrayList<>();
        preOrder(root, res);
        return res;
    }
    
    private void preOrder(Node node, List<Integer> res) {
        if (node == null) return;
        res.add(node.data);
        preOrder(node.left, res);
        preOrder(node.right, res);
    }

    List<Integer> inOrder() {
        List<Integer> res = new ArrayList<>();
        inOrder(root, res);
        return res;
    }
    
    private void inOrder(Node node, List<Integer> res) {
        if (node == null) return;
        inOrder(node.left, res);
        res.add(node.data);
        inOrder(node.right, res);
    }

    List<Integer> postOrder() {
        List<Integer> res = new ArrayList<>();
        postOrder(root, res);
        return res;
    }
    
    private void postOrder(Node node, List<Integer> res) {
        if (node == null) return;
        postOrder(node.left, res);
        postOrder(node.right, res);
        res.add(node.data);
    }

    List<List<Integer>> levelOrder() {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;
        
        Queue<Node> q = new LinkedList<>();
        q.add(root);

        while (!q.isEmpty()) {
            int size = q.size();
            List<Integer> level = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                Node curr = q.poll();
                level.add(curr.data);
                if (curr.left != null) q.add(curr.left);
                if (curr.right != null) q.add(curr.right);
            }
            res.add(level);
        }
        return res;
    }

    int height() {
        return height(root);
    }
    
    private int height(Node root) {
        if (root == null) return -1;
        int leftHeight = height(root.left);
        int rightHeight = height(root.right);
        return Math.max(leftHeight, rightHeight) + 1;
    }

    void insert(int data) {
        if (root == null) {
            root = new Node(data);
            return;
        }

        Queue<Node> q = new LinkedList<>();
        q.add(root);

        while (!q.isEmpty()) {
            Node curr = q.poll();
            if (curr.left == null) {
                curr.left = new Node(data);
                return;
            } else {
                q.add(curr.left);
            }

            if (curr.right == null) {
                curr.right = new Node(data);
                return;
            } else {
                q.add(curr.right);
            }
        }
    }

    void delete(int key) {
        if (root == null) return;
        
        root = deletion(root, key);
    }
    
    private Node deletion(Node root, int key) {
        if (root == null) return null;

        Queue<Node> q = new LinkedList<>();
        q.add(root);
        Node keyNode = null, lastNode = null;

        while (!q.isEmpty()) {
            lastNode = q.poll();
            if (lastNode.data == key) keyNode = lastNode;
            if (lastNode.left != null) q.add(lastNode.left);
            if (lastNode.right != null) q.add(lastNode.right);
        }

        if (keyNode != null) {
            keyNode.data = lastNode.data;
            deleteDeepest(root, lastNode);
        }

        return root;
    }
    
    private void deleteDeepest(Node root, Node delNode) {
        if (root == null) return;
        
        Queue<Node> q = new LinkedList<>();
        q.add(root);

        while (!q.isEmpty()) {
            Node curr = q.poll();
            if (curr.left != null) {
                if (curr.left == delNode) {
                    curr.left = null;
                    return;
                } else {
                    q.add(curr.left);
                }
            }
            if (curr.right != null) {
                if (curr.right == delNode) {
                    curr.right = null;
                    return;
                } else {
                    q.add(curr.right);
                }
            }
        }
    }

    boolean isFull() {
        return isFull(root);
    }
    
    private boolean isFull(Node node) {
        if (node == null) return true;
        
        if (node.left == null && node.right == null) return true;
        
        if (node.left != null && node.right != null) {
            return isFull(node.left) && isFull(node.right);
        }
        
        return false;
    }

    void createFullBinaryTree() {
        root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        
        root.left.left = new Node(4);
        root.left.right = new Node(5);
        
        root.right.left = new Node(6);
        root.right.right = new Node(7);
        
        root.left.left.left = new Node(8);
        root.left.left.right = new Node(9);
    }
}

class BST {
    Node root;
    
    BST() {
        root = null;
    }
    
    void insert(int data) {
        root = insertRec(root, data);
    }
    
    private Node insertRec(Node root, int data) {
        if (root == null) {
            root = new Node(data);
            return root;
        }
        
        if (data < root.data) {
            root.left = insertRec(root.left, data);
        } else if (data > root.data) {
            root.right = insertRec(root.right, data);
        }
        
        return root;
    }
    
    void createFromArray(int[] arr) {
        for (int value : arr) {
            insert(value);
        }
    }
    
    List<Integer> inOrder() {
        List<Integer> res = new ArrayList<>();
        inOrder(root, res);
        return res;
    }
    
    private void inOrder(Node node, List<Integer> res) {
        if (node == null) return;
        inOrder(node.left, res);
        res.add(node.data);
        inOrder(node.right, res);
    }
}

class BalancedTree {
    Node root;

    Node createBalancedTree(int[] arr) {
        Arrays.sort(arr);
        root = createBalancedTreeUtil(arr, 0, arr.length - 1);
        return root;
    }
    
    private Node createBalancedTreeUtil(int[] arr, int start, int end) {
        if (start > end) return null;
        
        int mid = (start + end) / 2;
        Node node = new Node(arr[mid]);
        
        node.left = createBalancedTreeUtil(arr, start, mid - 1);
        node.right = createBalancedTreeUtil(arr, mid + 1, end);
        
        return node;
    }
    
    boolean isBalanced() {
        return checkBalance(root) != -1;
    }
    
    private int checkBalance(Node node) {
        if (node == null) return 0;
        
        int leftHeight = checkBalance(node.left);
        if (leftHeight == -1) return -1;
        
        int rightHeight = checkBalance(node.right);
        if (rightHeight == -1) return -1;
        
        if (Math.abs(leftHeight - rightHeight) > 1) return -1;
        
        return Math.max(leftHeight, rightHeight) + 1;
    }
}

public class BinaryTreesLab4 {
    public static void main(String[] args) {
        System.out.println("=== Binary Tree Operations Demo ===\n");
        
        System.out.println("1. Regular Binary Tree:");
        BinaryTree tree = new BinaryTree();
        
        int[] values = {1, 2, 3, 4, 5, 6, 7};
        for (int value : values) {
            tree.insert(value);
        }
        
        System.out.println("   Tree height: " + tree.height());
        System.out.println("   Preorder: " + tree.preOrder());
        System.out.println("   Inorder: " + tree.inOrder());
        System.out.println("   Postorder: " + tree.postOrder());
        System.out.println("   Level Order: " + tree.levelOrder());
        
        System.out.println("\n2. Full Binary Tree:");
        BinaryTree fullTree = new BinaryTree();
        fullTree.createFullBinaryTree();
        
        System.out.println("   Height: " + fullTree.height());
        System.out.println("   Is full? " + fullTree.isFull());
        System.out.println("   Level Order: " + fullTree.levelOrder());
        
        System.out.println("\n3. Binary Search Tree (BST):");
        BST bst = new BST();
        int[] bstValues = {50, 30, 70, 20, 40, 60, 80};
        bst.createFromArray(bstValues);
        
        System.out.println("   Inorder (sorted): " + bst.inOrder());
        
        System.out.println("\n4. Balanced Binary Tree:");
        BalancedTree balancedTree = new BalancedTree();
        int[] sortedArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        balancedTree.createBalancedTree(sortedArray);
        
        System.out.println("   Is balanced? " + balancedTree.isBalanced());
        
        System.out.println("\n5. Node deletion demo:");
        BinaryTree demoTree = new BinaryTree();
        for (int i = 1; i <= 7; i++) {
            demoTree.insert(i);
        }
        
        System.out.println("   Before deletion: " + demoTree.levelOrder());
        demoTree.delete(3);
        System.out.println("   After deleting node 3: " + demoTree.levelOrder());
        
        System.out.println("\n=== All operations completed successfully ===");
    }
}