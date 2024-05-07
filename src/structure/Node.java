package structure;

public class Node<K extends Comparable<K>, V> {
    K key;
    V value;
    boolean isBlack;
    Node<K, V> parent;
    Node<K,V> left;
    Node<K,V> right;
    public Node(K key, V value){
        this.key = key;
        this.value = value;
        this.isBlack = false;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public boolean isBlack() {
        return isBlack;
    }

    public void setBlack(boolean black) {
        isBlack = black;
    }

    public Node<K,V> getParent() {
        return parent;
    }

    public void setParent(Node<K,V> parent) {
        this.parent = parent;
    }

    public Node<K,V> getLeft() {
        return left;
    }

    public void setLeft(Node<K,V> left) {
        this.left = left;
    }

    public Node<K,V> getRight() {
        return right;
    }

    public void setRight(Node<K,V> right) {
        this.right = right;
    }
}
