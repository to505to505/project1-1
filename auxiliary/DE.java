//import java.lang.reflect.Field;

public class DE<T> {

    public static void main(String[] args) {
        DE<Integer> i = new DE(21);
        System.out.println(i.getValue().getClass().getName());
        //Field f = i.getValue();
        if(i.getValue().getClass() == Integer.class)
        System.out.println("Y");
    }

    private Object value;
    
    public DE(Object value){
        if(value!=null) this.value = (T)value;
    }

    public T getValue(){
        return (T)value;
    }

    public void setValue(Object value) {
        if(value != null) this.value = value;
    }
}
