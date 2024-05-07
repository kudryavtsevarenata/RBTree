package structure;

public class RBTree<K extends Comparable<K>,V> {
    Node<K,V> root;
    public RBTree(){
    }
    public RBTree(K key, V value){
        this.root = new Node<>(key, value);
        this.root.setBlack(true);
    }
    private void leftRotation(Node<K,V> node){
        Node<K,V> parent = node.getParent();
        Node<K,V> grand = parent.getParent();

        if (grand != null && grand.getRight() == parent){
            grand.setRight(node);
        } else if (grand != null){
            grand.setLeft(node);
        }
        if (grand == null){
            this.root = node;
        }
        node.setParent(grand);

        parent.setRight(node.getLeft());
        if (node.getLeft() != null)
            node.getLeft().setParent(parent);

        parent.setParent(node);
        node.setLeft(parent);
    }
    private void rightRotation(Node<K,V> node){
        Node<K,V> parent = node.getParent();
        Node<K,V> grand = parent.getParent();

        if (grand != null && grand.getRight() == parent){
            grand.setRight(node);
        } else if (grand != null){
            grand.setLeft(node);
        }
        if (grand == null){
            this.root = node;
        }
        parent.setLeft(null);
        node.setParent(grand);

        parent.setLeft(node.getRight());
        if (node.getRight() != null )
            node.getRight().setParent(parent);

        parent.setParent(node);
        node.setRight(parent);
    }
    private boolean isLined(Node<K,V> node){
        return node.getParent().getRight() == node && node.getParent().getParent().getRight() == node.getParent() ||
                node.getParent().getLeft() == node && node.getParent().getParent().getLeft() == node.getParent();
    }
    private void selfBalance(Node<K,V> node){
        if (node.equals(this.root)){
            node.setBlack(true);
            return;
        }
        while (node.getParent() != null && !node.getParent().isBlack()){
            Node<K,V> uncle;
            Node<K,V> parent = node.getParent();
            Node<K,V> grand = parent.getParent();
            if (parent.equals(grand.getLeft())){
                uncle = grand.getRight();
            } else{
                uncle = grand.getLeft();
            }
            if (uncle != null && !uncle.isBlack()){
                uncle.setBlack(true);
                parent.setBlack(true);
                grand.setBlack(false);
            } else{
                if (!isLined(node)){
                    // prepare
                    if (node.getParent().getLeft() == node){
                        rightRotation(node);
                        node = node.getRight();
                    } else{
                        leftRotation(node);
                        node = node.getLeft();
                    }
                }
                // rotate
                Node<K,V> p = node.getParent();
                Node<K,V> g = p.getParent();
                if (node.getParent().getRight() == node &&
                       node.getParent().getParent().getRight() == node.getParent()){
                    leftRotation(node.getParent());
                } else{
                    rightRotation(node.getParent());
                }
                p.setBlack(true);
                g.setBlack(false);
            }
            node = grand;
        }
        this.root.setBlack(true);
    }
    public void insert(K key, V value){
        Node<K,V> node = new Node<>(key, value);
        if (this.root == null){
            node.setBlack(true);
            this.root = node;
            return;
        }
        Node<K,V> current = this.root;
        Node<K,V> parent = null;
        while (current != null){
            parent = current;
            if (current.getKey().compareTo(key) > 0){
                current = current.getLeft();
            } else{
                current = current.getRight();
            }
        }
        node.setParent(parent);
        if (parent.getKey().compareTo(key) > 0){
            parent.setLeft(node);
        } else{
            parent.setRight(node);
        }
        selfBalance(node);
    }
    public void remove(K value){

    }
    public void join(RBTree<K,V> t){

    }
    public void split(K key){

    }
    public Node<K,V> searchNode(K key){
        Node<K,V> current = this.root;
        while(current != null){
            if (current.getKey().equals(key)){
                return current;
            }
            if (current.getKey().compareTo(key) > 0){
                current = current.getLeft();
            } else{
                current = current.getRight();
            }
        }
        return null;
    }
    // TODO: удаление,
    // TODO: объединение,
    // TODO: разделение,
}
