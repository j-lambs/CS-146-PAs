package Trees;

public class BinarySearchTree {
    private BinaryNode root = new BinaryNode();
    private int size;

    public BinarySearchTree() {
        this.root = null;
    }
    public BinarySearchTree(Integer value) {
        this.root = new BinaryNode(value);
    }

    // GETTERS
    public BinaryNode getRoot() {
        return root;
    }
    public int getSize() {
        return this.size;
    }

    // PRINTING
    public void printInOrder() {
        // if curNode has left node, go left
        // if no left, print, then go right
        printInOrderHelper(root);
        System.out.println();
    }
    private void printInOrderHelper(BinaryNode node) {
        if (node == null) {
            return;
        }
        printInOrderHelper(node.getLeftNode());
        System.out.print(node.getValue() + " ");
        printInOrderHelper(node.getRightNode());
    }

    // pre-order good for making deep copies
    public void printPreOrder() {
        // if curNode has left node, go left
        // if no left, print, then go right
        printPreOrderHelper(root);
        System.out.println();
    }
    private void printPreOrderHelper(BinaryNode node) {
        if (node == null) {
            return;
        }
        System.out.print(node.getValue() + " ");
        printPreOrderHelper(node.getLeftNode());
        printPreOrderHelper(node.getRightNode());
    }

    // DFS, good for deleting a tree, always go to leaves :)
    public void printPostOrder() {
        // if curNode has left node, go left
        // if no left, print, then go right
        printPostOrderHelper(root);
        System.out.println();
    }
    private void printPostOrderHelper(BinaryNode node) {
        if (node == null) {
            return;
        }
        printPostOrderHelper(node.getLeftNode());
        printPostOrderHelper(node.getRightNode());
        System.out.print(node.getValue() + " ");
    }

    public void insert(Integer value) {
        BinaryNode newNode = new BinaryNode(value);
        BinaryNode currentNode = root;
        boolean notInserted = true;

        if (root == null) {
            root = new BinaryNode(value);
            ++size;
        }
        else {
            while (notInserted) {
                if (value <= currentNode.getValue()) {
                    if (currentNode.getLeftNode() == null) {
                        currentNode.setLeftNode(newNode);
                        size++;
                        notInserted = false;
                    } else {
                        currentNode = currentNode.getLeftNode();
                    }
                }
                else {
                    if (currentNode.getRightNode() == null) {
                        currentNode.setRightNode(newNode);
                        size++;
                        notInserted = false;
                    } else {
                        currentNode = currentNode.getRightNode();
                    }
                }
            }
        }
    }
}
