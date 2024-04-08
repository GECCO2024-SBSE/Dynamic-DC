import java.util.LinkedList;
import java.util.Queue;

public class BinTree {
    public static class Node {
        int data;
        Node left;
        Node right;

        public Node(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;

    public void BinaryTreeOperations() {
        root = null;
    }

    // Insert a node into the binary tree
    public void insert(int value) {
        root = insert(root, value);
    }

    private Node insert(Node node, int value) {
        if (node == null) {
            return new Node(value);
        }

        if (value < node.data) {
            node.left = insert(node.left, value);
        } else if (value > node.data) {
            node.right = insert(node.right, value);
        }

        return node;
    }

    // Search for a node in the binary tree
    public Node search(int value) {
        return search(root, value);
    }

    private Node search(Node node, int value) {
        while (node != null) {
            if (value == node.data) {
                return node;
            } else if (value < node.data) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return null;
    }

    // Inorder traversal
    public void inOrder() {
        inOrder(root);
        System.out.println();
    }

    private void inOrder(Node node) {
        if (node != null) {
            inOrder(node.left);
            System.out.print(node.data + " ");
            inOrder(node.right);
        }
    }

    // Preorder traversal
    public void preOrder() {
        preOrder(root);
        System.out.println();
    }

    private void preOrder(Node node) {
        if (node != null) {
            System.out.print(node.data + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    // Postorder traversal
    public void postOrder() {
        postOrder(root);
        System.out.println();
    }

    private void postOrder(Node node) {
        if (node != null) {
            postOrder(node.left);
            postOrder(node.right);
            System.out.print(node.data + " ");
        }
    }

    // Find the largest node in the binary tree
    public int findLargest() {
        return findLargest(root);
    }

    private int findLargest(Node node) {
        if (node == null) {
            System.out.println("Tree is empty");
            return 0;
        }

        int leftMax, rightMax;
        int max = node.data;

        if (node.left != null) {
            leftMax = findLargest(node.left);
            max = Math.max(max, leftMax);
        }

        if (node.right != null) {
            rightMax = findLargest(node.right);
            max = Math.max(max, rightMax);
        }

        return max;
    }

    // Find the smallest node in the binary tree
    public int findSmallest() {
        return findSmallest(root);
    }

    private int findSmallest(Node node) {
        if (node == null) {
            System.out.println("Tree is empty");
            return 0;
        }

        int leftMin, rightMin;
        int min = node.data;

        if (node.left != null) {
            leftMin = findSmallest(node.left);
            min = Math.min(min, leftMin);
        }

        if (node.right != null) {
            rightMin = findSmallest(node.right);
            min = Math.min(min, rightMin);
        }

        return min;
    }

    // Find the maximum width of the binary tree
    public int findMaximumWidth() {
        return findMaximumWidth(root);
    }

    private int findMaximumWidth(Node root) {
        if (root == null) {
            System.out.println("Tree is empty");
            return 0;
        }

        int maxWidth = 0;
        int nodesInLevel;

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            nodesInLevel = queue.size();
            maxWidth = Math.max(maxWidth, nodesInLevel);

            while (nodesInLevel > 0) {
                Node current = queue.remove();
                if (current.left != null) {
                    queue.add(current.left);
                }
                if (current.right != null) {
                    queue.add(current.right);
                }
                nodesInLevel--;
            }
        }

        return maxWidth;
    }

    // Delete a node from the binary tree
    public void delete(int value) {
        root = delete(root, value);
    }

    private Node delete(Node root, int value) {
        if (root == null) {
            return root;
        }

        if (value < root.data) {
            root.left = delete(root.left, value);
        } else if (value > root.data) {
            root.right = delete(root.right, value);
        } else {
            // Node with only one child or no child
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            // Node with two children: Get the inorder successor (smallest
            // in the right subtree)
            root.data = minValue(root.right);

            // Delete the inorder successor
            root.right = delete(root.right, root.data);
        }

        return root;
    }

    private int minValue(Node root) {
        int minValue = root.data;
        while (root.left != null) {
            minValue = root.left.data;
            root = root.left;
        }
        return minValue;
    }


    }

