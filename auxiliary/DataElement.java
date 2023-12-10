import java.io.CharConversionException;
import java.util.IllegalFormatConversionException;

public class DataElement {
    private Object value;
    private DataElementType type;

    public DataElement(Object value){
        setValue(value);
    }

    public DataElement(DataElementType type){
        this.type = type;
        this.value = null;
    }

    public DataElement(Object value, DataElementType type){
        this.type = type;
        
        if (value instanceof Double){
            if(this.type == DataElementType.DOUBLE)
                this.value = value;
            else if(this.type == DataElementType.INTEGER)
                this.value = (double) value;
            else if (this.type == DataElementType.STRING)
                this.value = String.valueOf(value);
            else
                this.value = null;
        }
        else if (value instanceof Integer){
            if(this.type == DataElementType.DOUBLE)
            try{
                this.value = (int) value;
            }
            catch (Exception ex){
                System.out.println("Invalid double to integer conversion");
            }
            else if(this.type == DataElementType.INTEGER)
                this.value = value;
            else if (this.type == DataElementType.STRING)
                this.value = String.valueOf(value);
            else
                this.value = null;
        }
        else if (value instanceof String) {
            if (this.type == DataElementType.STRING){
                this.value = value;
            }
            else{
                this.value = "";
            }
        }
        else {
            this.value = null;
            this.type = null;
        }
    }


    public DataElementType getType() {
        return this.type;
    }

    public int asInt(){
        try{
            return (int) value;
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }
    }

    public Object getValue(){
        return this.value;
    }

    public double asDouble() {
        try{
            return (double) value;
        } catch (Exception e) {
            System.out.println(e);
            return 0.0;
        }
    }

    public String asString(){
        try{
            return (String) value;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    
    public void setValue(Object value) {
        try{
            if(value instanceof Integer)
                type = DataElementType.INTEGER;
            else if (value instanceof Double)
                type = DataElementType.DOUBLE;
            else if (value instanceof String)
                type = DataElementType.STRING;
            else 
                type = null;
            this.value = value;
        } catch (Exception e) {
            System.out.println(e);
            this.value = null;
        }
    }
}
