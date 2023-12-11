package data.deprecated;

import java.util.ArrayList;

/**
 * Deprecated
 */
public class Cell {

    public static void main(String[] args) {
        Byte x1 = 32;
        Short x2 = 100;
        Long x3 = 12345678L;
        Integer x4 = 1234567890;
        Float x5 = 1234.5F;
        Double x6 = 1234567890.12345;
        Character x7 = 'S';
        String x8 = "Trying to do our best here !";
        Object x9 = new Exception();
        Object x10 = null;

        Cell c1 = new Cell(x1);
        Cell c2 = new Cell(x2);
        Cell c3 = new Cell(x3);
        Cell c4 = new Cell(x4);
        Cell c5 = new Cell(x5);
        Cell c6 = new Cell(x6);
        Cell c7 = new Cell(x7);
        Cell c8 = new Cell(x8);
        Cell c9 = new Cell(x9);
        Cell c10 = new Cell(x10);

        System.out.println("c1.type.toString(): " + c1.type);
        // return: "c1.type.toString(): class java.lang.Byte"

        System.out.println("x1.getClass().toString(): " + x1.getClass().toString());
        // return: "x1.getClass().toString(): class java.lang.Byte"

        Byte r = (Byte)c1.getValue("Byte");
        System.out.println("x1: " + x1.toString() + " , c1: " + c1.getValue("Byte") + " r: " + r.toString());

//       if (c1.getValue(Byte.class) != x1) {
//            String msg = "c1=" + (c1.getValue(Byte.class)).toString() + " , x1=" + x1.toString();
//            System.out.println(msg);
//        }

    }
    private Object value;
    private String type;

    public Cell (Object aValue) { this.value = aValue; this.type = "Object"; }
    public Cell (Byte aValue) {
        this.value = aValue;
        this.type = Byte.class.toString();
    }
    public Cell (Short aValue) { this.value = aValue; this.type = "Short"; }
    public Cell (Long aValue) { this.value = aValue; this.type = "Long"; }
    public Cell (Integer aValue) { this.value = aValue; this.type = "Integer"; }
    public Cell (Float aValue) { this.value = aValue; this.type = "Float"; }
    public Cell (Double aValue) { this.value = aValue; this.type = "Double"; }
    public Cell (Character aValue) { this.value = aValue; this.type = "Character"; }
    public Cell (String aValue) { this.value = aValue; this.type = "String"; }

    public Object getValue(Object asType) {
        if (asType == null) { return this.value; }
        else if (asType == "Object") { return this.value; }
        else if (asType == "Byte") { return (Byte) this.value; }
        else if (asType == "Short") { return (Short) this.value; }
        else if (asType == "Long") { return (Long) this.value; }
        else if (asType == "Integer") { return (Integer) this.value; }
        else if (asType == "Float") { return (Float) this.value; }
        else if (asType == "Double") { return (Double) this.value; }
        else if (asType == "Character") { return (Character) this.value; }
        else if (asType == "String") { return (String) this.value; }
        else { return null; }
    }
}

