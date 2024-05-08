import org.junit.Assert;
import org.junit.Test;
import structure.Node;
import structure.RBTree;

public class RedBlackTreeTest {
    @Test
    public void testRebBlackTree(){
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
        Assert.assertTrue(checkParentOfRedIsBlack(rb));
        Assert.assertNotNull(rb.searchNode(16));
        Assert.assertNull(rb.searchNode(-4));
    }
    @Test
    public void testSearchSucceeded(){
        RBTree<Integer, Integer> rb = new RBTree<>();
        rb.insert(5, 5);
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
        Assert.assertNotNull(rb.searchNode(16));
    }
    @Test
    public void testSearchFailed(){
        RBTree<Integer, Integer> rb = new RBTree<>();
        rb.insert(5, 5);
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
        Assert.assertNull(rb.searchNode(-4));
    }
    private <K extends Comparable<K>,V> boolean checkParentOfRedIsBlack(RBTree<K,V> rb){
        return checkParentOfRedIsBlackHelper(rb.getRoot());
    }
    private <K extends Comparable<K>,V> boolean checkParentOfRedIsBlackHelper(Node<K,V> node){
        boolean isCorrect = true;
        if (node != null){
            isCorrect = checkParentOfRedIsBlackHelper(node.getLeft());
            if (!node.isBlack() && node.getParent() != null && !node.getParent().isBlack()){
                isCorrect = false;
            }
            isCorrect = isCorrect && checkParentOfRedIsBlackHelper(node.getRight());
        }
        return isCorrect;
    }
}
