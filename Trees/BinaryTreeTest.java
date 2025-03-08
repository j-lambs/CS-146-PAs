package Trees;
import java.util.*;

public class BinaryTreeTest {
    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        bst.insert(50);
        bst.insert(20); bst.insert(75);
        bst.insert(10); bst.insert(30); bst.insert(60); bst.insert(100);
        bst.insert(5);
        bst.printInOrder();
        bst.printPreOrder();
        bst.printPostOrder();
        System.out.println("Size: " + bst.getSize());
    }
}
