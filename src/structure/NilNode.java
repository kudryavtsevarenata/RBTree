package structure;

public class NilNode extends Node{
    private static final NilNode instance = new NilNode();
    private NilNode() {
        super();
        this.isBlack = true;
    }

    public static NilNode getInstance() {
        return instance;
    }
}
