import structure.Node;
import structure.Pair;
import structure.RBTree;

public class Main {
    public static void main(String[] args) {
        RBTree<Integer, Integer> rb = new RBTree<>();
        rb.insert(5, 5);
        rb.insert(2, 2);
        rb.insert(6, 6);

        RBTree<Integer, Integer> rb2 = new RBTree<>();
        rb2.insert(58, 58);
        rb2.insert(36, 36);
        rb2.insert(64, 64);
        rb2.insert(20, 20);
        rb2.insert(48, 48);
        rb2.insert(60, 60);
        rb2.insert(80, 80);
        rb2.insert(17, 17);
        rb2.insert(40, 40);
        rb2.insert(59, 59);

        Pair<Node<Integer, Integer>> sp =  rb2.split(42);
    }
}