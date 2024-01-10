package prediction;

import java.util.ArrayList;

public interface INode {
    public INode getParent();
    public ArrayList<Node> getChildren();
    
    public default boolean isRoot(){
        return getParent() == null;
    }
    public default boolean isDecision(){
        return getChildren().size() > 0;
    }
    public default boolean isLeaf(){
        return getChildren().size() == 0;
    }
}