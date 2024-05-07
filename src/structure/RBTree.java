package structure;

public class RBTree<T extends Comparable<T>> {
    Node<T> root;
    public RBTree(){
    }
    public RBTree(T value){
        this.root = new Node<>(value);
        this.root.setBlack(true);
    }
    private void leftRotation(Node<T> node){
        Node<T> parent = node.getParent();
        Node<T> grand = parent.getParent();

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
    private void rightRotation(Node<T> node){
        Node<T> parent = node.getParent();
        Node<T> grand = parent.getParent();

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
    private boolean isLined(Node<T> node){
        return node.getParent().getRight() == node && node.getParent().getParent().getRight() == node.getParent() ||
                node.getParent().getLeft() == node && node.getParent().getParent().getLeft() == node.getParent();
    }
    private void selfBalance(Node<T> node){
        if (node.equals(this.root)){
            node.setBlack(true);
            return;
        }
        while (node.getParent() != null && !node.getParent().isBlack()){
            Node<T> uncle;
            Node<T> parent = node.getParent();
            Node<T> grand = parent.getParent();
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
                Node<T> p = node.getParent();
                Node<T> g = p.getParent();
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
    public void insert(T value){
        Node<T> node = new Node<>(value);
        if (this.root == null){
            node.setBlack(true);
            this.root = node;
            return;
        }
        Node<T> current = this.root;
        Node<T> parent = null;
        while (current != null){
            parent = current;
            if (current.getValue().compareTo(value) > 0){
                current = current.getLeft();
            } else{
                current = current.getRight();
            }
        }
        node.setParent(parent);
        if (parent.getValue().compareTo(value) > 0){
            parent.setLeft(node);
        } else{
            parent.setRight(node);
        }
        selfBalance(node);
    }
    public void remove(T value){

    }
    public void join(RBTree<T> t){

    }
    public void split(T key){

    }
    // TODO: удаление,
    // TODO: объединение,
    // TODO: разделение,
    // TODO: поиск,
    // TODO: добавить ключи и значения
}
