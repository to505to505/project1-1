package prediction;

import java.util.ArrayList;

import data.DataPartition;

public class Node implements INode{
    public Node parent;
    public ArrayList<Node> children;

    private double value = 0;

    public DataPartition dataPartition;

    public void makeLeaf(boolean problemType, int targetIndex){
        if(problemType){
            for(int i = 0; i < dataPartition.studentIndexes.size(); i++)
                value += dataPartition.data.data[dataPartition.studentIndexes.get(i)][targetIndex];
            value /= dataPartition.studentIndexes.size();
        }
        else{
            int[] freq = new int[11];
            for(int i = 0; i < dataPartition.studentIndexes.size(); i++)
                freq[(int)Math.round(dataPartition.data.data[dataPartition.studentIndexes.get(i)][targetIndex])]++;
            int max = 0;
            for(int i = 0; i < 11; i++)
                if(freq[i] > freq[max])
                    max = i;
            value = max;
        }
    }
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

    double getValue(){
        return value;
    }
}
