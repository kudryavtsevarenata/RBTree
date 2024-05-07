package structure;

public class Node<T extends Comparable<T>> {
    T value;
    boolean isBlack;
    Node<T> parent;
    Node<T> left;
    Node<T> right;
    public Node(T value){
        this.value = value;
        this.isBlack = false;
    }

    public T getValue() {
        return value;
    }

    public boolean isBlack() {
        return isBlack;
    }

    public void setBlack(boolean black) {
        isBlack = black;
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public Node<T> getLeft() {
        return left;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }
}
