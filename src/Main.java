import structure.RBTree;

public class Main {
    public static void main(String[] args) {
        RBTree<Integer, Integer> rb = new RBTree<>(5, 5);
        rb.insert(10, 10);
        rb.insert(2, 2);
        rb.insert(16, 16);
        rb.insert(16,16);
        rb.insert(12, 12);
        rb.insert(34, 34);
        rb.insert(28, 28);
        rb.insert(20, 20);
        rb.insert(1, 1);
        rb.insert(3, 3);
        rb.remove(34);
    }
}