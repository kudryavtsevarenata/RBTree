package structure;

public class RBTree<K extends Comparable<K>,V> {
    Node<K,V> root;
    public RBTree(){
        this.root = NilNode.getInstance();
    }
    public RBTree(K key, V value){
        this.root = new Node<>(key, value);
        this.root.setBlack(true);
        this.root.setLeft(NilNode.getInstance());
        this.root.setRight(NilNode.getInstance());
    }
    public RBTree(Node<K,V> node){
        this.root = node;
    }

    public Node<K, V> getRoot() {
        return root;
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
        node.setParent(grand);
        parent.setLeft(node.getRight());

        node.getRight().setParent(parent);

        parent.setParent(node);
        node.setRight(parent);
    }
    private boolean isLined(Node<K,V> node){
        return node.getParent().getRight() == node && node.getParent().getParent().getRight() == node.getParent() ||
                node.getParent().getLeft() == node && node.getParent().getParent().getLeft() == node.getParent();
    }
    private void fixInsert(Node<K,V> node){
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
            if (!uncle.isBlack()){
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
                if (p.getRight() == node &&
                       g.getRight() == p){
                    leftRotation(p);
                } else{
                    rightRotation(p);
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
        if (this.root == NilNode.getInstance()){
            node.setBlack(true);
            this.root = node;
            this.root.setLeft(NilNode.getInstance());
            this.root.setRight(NilNode.getInstance());
            return;
        }
        Node<K,V> current = this.root;
        Node<K,V> parent = null;
        while (current != NilNode.getInstance()){
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
        node.setLeft(NilNode.getInstance());
        node.setRight(NilNode.getInstance());
        fixInsert(node);
    }
    public void remove(K key){
        if (key == null){
            return;
        }
        Node<K,V> toRemove = searchNode(key);
        if (toRemove == null){
            return;
        }
        boolean needToBalance;
        Node<K,V> movedUp;
        if (!(toRemove.getLeft() != NilNode.getInstance() && toRemove.getRight() != NilNode.getInstance())){
            movedUp = removeWithZeroOrOne(toRemove);
            needToBalance = toRemove.isBlack();
        } else{
            Node<K,V> inOrderSuccessor = findMin(toRemove.getRight());
            toRemove.setKey(inOrderSuccessor.getKey());
            toRemove.setValue(inOrderSuccessor.getValue());

            movedUp = removeWithZeroOrOne(inOrderSuccessor);
            needToBalance = inOrderSuccessor.isBlack();
        }
        if (needToBalance){
            fixDeletion(movedUp);
        }
    }
    private void fixDeletion(Node<K,V> node){
        Node<K,V> parent = node.getParent();
        Node<K,V> brother;
        while (node.isBlack() && node != this.root){
            if (node.getParent().getLeft() == node){
                brother = parent.getRight();
                if (!brother.isBlack()){
                    brother.setBlack(true);
                    parent.setBlack(false);
                    leftRotation(brother);
                }
                if (brother.getLeft().isBlack() && brother.getRight().isBlack()){
                    brother.setBlack(false);
                } else {
                    if (brother.getRight().isBlack()){
                        brother.getLeft().setBlack(true);
                        brother.setBlack(false);
                        rightRotation(brother.getLeft());
                    }
                    brother.setBlack(parent.isBlack());
                    parent.setBlack(true);
                    brother.getRight().setBlack(true);
                    leftRotation(parent.getRight());
                    node = this.root;
                }
            } else{
                brother = parent.getLeft();
                if (!brother.isBlack()){
                    brother.setBlack(true);
                    parent.setBlack(false);
                    rightRotation(brother);
                }
                if (brother.getLeft().isBlack() && brother.getRight().isBlack()){
                    brother.setBlack(false);
                }
                else{
                    if (brother.getLeft().isBlack()){
                        brother.getRight().setBlack(true);
                        brother.setBlack(false);
                        leftRotation(brother.getRight());
                    }
                    brother.setBlack(parent.isBlack());
                    parent.setBlack(true);
                    brother.getLeft().setBlack(true);
                    rightRotation(parent.getLeft());
                    node = this.root;
                }
            }
        }
        node.setBlack(true);
        this.root.setBlack(true);
    }
    private Node<K,V> removeWithZeroOrOne(Node<K, V> toRemove){
        Node<K,V> parent = toRemove.getParent();
        Node<K,V> toBind;
        if (parent == null){
            if (toRemove.getLeft() != NilNode.getInstance()){
                this.root = toRemove.getLeft();
                toBind = toRemove.getLeft();
            } else{
                this.root = toRemove.getRight();
                toBind = toRemove.getRight();
            }
        } else{
            if (toRemove.getLeft() != NilNode.getInstance()){
                toBind = toRemove.getLeft();
            } else{
                toBind = toRemove.getRight();
            }
            if (parent.getLeft() == toRemove){
                parent.setLeft(toBind);
            } else{
                parent.setRight(toBind);
            }
            toBind.setParent(parent);
        }
        toRemove.setParent(null);
        return toBind;
    }
    private Node<K,V> findMin(Node<K,V> node){
        if (node == NilNode.getInstance()){
            return node;
        }
        while (node.getLeft() != NilNode.getInstance()){
            node = node.getLeft();
        }
        return node;
    }
    private Node<K,V> findMax(Node<K,V> node){
        if (node == NilNode.getInstance()){
            return node;
        }
        while (node.getRight() != NilNode.getInstance()){
            node = node.getRight();
        }
        return node;
    }
    private boolean checkCorrectForJoin(RBTree<K,V> t, K key){
        K maxValue = this.findMin(t.getRoot()).getKey();
        K minValue = t.findMax(this.root).getKey();
        return key.compareTo(minValue) >= 0 && key.compareTo(maxValue) <= 0;
    }
    public Node<K,V> join(RBTree<K,V> t, K key, V value){
        if (!this.checkCorrectForJoin(t, key)){
            throw new IllegalArgumentException("can't join, you should provide condition T1(this) <= key <= T2");
        }
        Node<K,V> node = joinHelper(this, t, key, value);
        this.root = node;
        return node;
    }
    private Node<K,V> joinHelper(RBTree<K,V> t1, RBTree<K,V> t2, K key, V value){
        int bh1 = getBlackHeight(t1.getRoot());
        int bh2 = getBlackHeight(t2.getRoot());
        Node<K,V> node = new Node<>(key, value);
        if (bh1 == bh2){
            node.setBlack(true);
            node.setLeft(t1.getRoot());
            t1.getRoot().setParent(node);
            node.setRight(t2.getRoot());
            t2.getRoot().setParent(node);
        } else if (bh1 > bh2){
            int curBH = bh1;
            Node<K,V> current = t1.getRoot();
            while (curBH != bh2){
                current = current.getRight();
                if (current.isBlack()){
                    --curBH;
                }
            }
            node.setParent(current.getParent());
            current.getParent().setRight(node);

            node.setLeft(current);
            node.setRight(t2.getRoot());

            current.setParent(node);
            t2.getRoot().setParent(node);

            fixInsert(node);

            node = t1.getRoot();
        } else{
            int curBH = bh2;
            Node<K,V> current = t2.getRoot();
            while (curBH != bh1){
                current = current.getLeft();
                if (current.isBlack()){
                    --curBH;
                }
            }
            node.setParent(current.getParent());
            current.getParent().setLeft(node);

            node.setLeft(t1.getRoot());
            node.setRight(current);

            current.setParent(node);
            t1.getRoot().setParent(node);

            fixInsert(node);
            node = t2.getRoot();
        }
        return node;
    }
    public Pair<Node<K,V>> split(K key){
        return splitHelper(this.root, key);
    }
    private Pair<Node<K,V>> splitHelper(Node<K,V> node, K key){
        if (node == NilNode.getInstance()){
            return new Pair<Node<K,V>>(NilNode.getInstance(), NilNode.getInstance());
        }
        if (key.compareTo(node.getKey()) < 0){
            Pair<Node<K,V>> p = splitHelper(node.getLeft(), key);
            return new Pair<>(p.getFirst(), joinHelper(new RBTree<>(p.getSecond()), new RBTree<>(node.getRight()), node.getKey(), node.getValue()));
        } else{
            Pair<Node<K,V>> p = splitHelper(node.getRight(), key);
            return new Pair<>(joinHelper(new RBTree<>(node.getLeft()), new RBTree<>(p.getFirst()), node.getKey(), node.getValue()), node.getRight());
        }
    }
    private int getBlackHeight(Node<K,V> node){
        int bh = 0;
        node = node.getLeft();
        while (node != NilNode.getInstance() && node != null){
            if (node.isBlack()){
                ++bh;
            }
            node = node.getLeft();
        }
        return bh;
    }
    public Node<K,V> searchNode(K key){
        Node<K,V> current = this.root;
        while(current != NilNode.getInstance()){
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
    public void inOrder(){
        inOrderHelper(this.root);
    }
    private void inOrderHelper(Node<K,V> node){
        if (node != NilNode.getInstance()){
            inOrderHelper(node.getLeft());
            System.out.println(node.getValue());
            inOrderHelper(node.getRight());
        }
    }
}
