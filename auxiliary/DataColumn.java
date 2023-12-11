import java.util.ArrayList;

import javax.swing.text.html.parser.Element;

public class DataColumn {
    private String name;
    private DataElementType type;
    private ArrayList<DataElement> elements;

    public static void main(String[] args) {
        DataElement s = new DataElement("s");
        DataElement d = new DataElement(10.2);
        DataElement i = new DataElement(11);
        DataElement ss = new DataElement("ss");
        DataColumn c = new DataColumn("c", DataElementType.DOUBLE);
        c.add(d); c.addDataElementOf("s");
        c.print();
    }

    public void print(){
        for(DataElement e : elements)
            System.out.println(e.asInt());
    }

    public DataColumn(String name, DataElementType type) {
        this.name = name;
        this.type = type;
        this.elements = new ArrayList<DataElement>();
    }

    public void addDataElementOf(Object value) {
        if (value==null)
            elements.add(new DataElement(this.type));
        else if(value instanceof Double && this.type == DataElementType.DOUBLE)
            elements.add(new DataElement(value));
        else if(value instanceof String && this.type == DataElementType.STRING)
            elements.add(new DataElement(value));
        else if(value instanceof Integer && this.type == DataElementType.INTEGER)
            elements.add(new DataElement(value));
        else elements.add(new DataElement(this.type));
    }

    public void add(DataElement element){
        elements.add(new DataElement(element.getValue(), this.type));
    }

    public int intAt(int index) {
        return elements.get(index).asInt();
    }

    public double doubleAt(int index) {
        return elements.get(index).asDouble();
    }

    public String stringAt(int index) {
        return elements.get(index).asString();
    }

    public int getCount(){
        return elements.size();
    }
    
    public int getCountOf(int value){
        int count = 0;
        for(DataElement e : elements)
            if(e.asInt() == value) count++;
        return count;
    }

    public int getCountOf(double value){
        int count = 0;
        for(DataElement e : elements)
            if(e.asDouble() == value) count++;
        return count;
    }

    public int getCountOf(String value){
        int count = 0;
        for(DataElement e : elements)
            if(e.asString().equals(value)) count++;
        return count;
    }


    public double getAverage(){
        if (this.type != DataElementType.INTEGER && this.type != DataElementType.DOUBLE)
            return -1;
            
        double sum = 0;
        for(DataElement e : elements){
            sum += e.asDouble();
        }
        return sum/elements.size();
    }

    public int getIntMax(){
        if (this.type != DataElementType.INTEGER)
            return -1;
        
        int max = 0;
        int aux;
        for(DataElement e : elements) {
            aux = e.asInt();
            if(aux > max) max = aux;
        }
        return max;
    }

    public double getDoubleMax(){
        if (this.type != DataElementType.DOUBLE)
            return -1;
        
        double max = 0;
        double aux;
        for(DataElement e : elements) {
            aux = e.asDouble();
            if(aux > max) max = aux;
        }
        return max;
    }


    public double getMin(){
        double min = 0.0; double val = 0.0;
        for(DataElement e : elements) {
            val = e.asDouble();
            if (val < min) { min = val;}
        }
        return val;
    }
}
