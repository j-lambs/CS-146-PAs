package Trees;

import java.util.*;

public class BinaryNode {
    private Integer valueInteger;
    private BinaryNode left;
    private BinaryNode right;

    public BinaryNode() {
        this.valueInteger = null;
        this.left = null;
        this.right = null;
    }
    public BinaryNode(Integer valueInteger) {
        this.valueInteger = valueInteger;
        this.left = null;
        this.right = null;
    }

    public BinaryNode(Integer valueInteger, BinaryNode left, BinaryNode right) {
        this.valueInteger = valueInteger;
        this.left = left;
        this.right = right;
    }

    // Getters
    public Integer getValue() {
        return this.valueInteger;
    }
    public BinaryNode getLeftNode() {
        return this.left;
    }
    public BinaryNode getRightNode() {
        return this.right;
    }
    // Setters
    public void setValue(Integer value) {
        this.valueInteger = value;
    }
    public void setLeftNode(BinaryNode newNode) {
        this.left = newNode;
    }
    public void setRightNode(BinaryNode newNode) {
        this.right = newNode;
    }
}
