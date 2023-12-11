package data;

import java.util.ArrayList;
import javax.sound.sampled.AudioFileFormat.Type;

import exceptions.TypeMismatchException;

/**
 * Data Column class of DE objects.
 */
public class DC<T> {
    private String name;
    private final Class<T> type;
    private ArrayList<DE> elements;

    public static void main(String[] args) {
        DE s = new DE("s");
        DE d = new DE(10.2);
        DE f = new DE(11.0);
        DE i = new DE(11);
        DE ss = new DE(null);
        DE[] a = {s, d, f, i, ss};
        DC<Double> c = new DC<Double>("c", Double.class);
        for(DE e : a)
            c.add(e);
        c.get(3).setValue(10L);
        c.print();
        System.out.println(c.get(3).getValue().getClass());
        }

    public DC(String name, Class<T> type) {
        this.name = name;
        this.type = type;
        this.elements = new ArrayList<DE>();
    }

    public void add(DE<?> element){
        if(element.getValue(type) != null)
            elements.add(new DE(element.getValue(type)));
        else if(element.getType() != this.type)
            addEmpty(type);
        else
            elements.add(element);
    }

    private <V> void addEmpty(Class<V> type) {
        Class<?> t = type;
        if (t == Double.class) {
            elements.add(new DE<Double>(Double.class));
        } else if (t == Integer.class) {
            elements.add(new DE<Integer>(Integer.class));
        } else if (t == String.class) {
            elements.add(new DE<String>(String.class));
        } else if (t == Boolean.class) {
            elements.add(new DE<Boolean>(Boolean.class));
        } else if (t == Character.class) {
            elements.add(new DE<Character>(Character.class));
        } else if (t == Long.class) {
            elements.add(new DE<Long>(Long.class));
        } else if (t == Float.class) {
            elements.add(new DE<Float>(Float.class));
        } else if (t == Short.class) {
            elements.add(new DE<Short>(Short.class));
        } else if (t == Byte.class) {
            elements.add(new DE<Byte>(Byte.class));
        } else if (t == Void.class) {
            elements.add(new DE<Void>(Void.class));
        } else {
            elements.add(new DE<Object>(Object.class));
        }
    }

    public DE get(int index){
        return elements.get(index);
    }

    public void print(){
        System.out.println("Name: " + name);
        System.out.println("Type: " + type);
        System.out.println("Elements: ");
        for(DE<Double> e : elements){
            System.out.println(elements.indexOf(e) + ": " + e.getType() + ": " + e.getValue());
        }
    }

    public Class<T> getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public ArrayList<DE> getElements() {
        return elements;
    }
}
