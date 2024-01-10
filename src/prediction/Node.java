package prediction;

import java.util.ArrayList;

public class Node implements INode{
    private Node parent;
    private ArrayList<Node> children;

    public Node(Node parent){
        this.parent = parent;
    }

    public Node(){
        this.parent = null;
    }

    public Node getParent(){
        return parent;
    }

    public ArrayList<Node> getChildren(){
        return children;
    }
}
