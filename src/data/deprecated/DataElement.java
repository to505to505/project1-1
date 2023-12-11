package data.deprecated;
import java.io.CharConversionException;
import java.util.IllegalFormatConversionException;

import data.DataType;


/**
 * Deprecated
 */
public class DataElement {
    private Object value;
    private DataType type;

    public DataElement(Object value){
        setValue(value);
    }

    public DataElement(DataType type){
        this.type = type;
        this.value = null;
    }

    public DataElement(Object value, DataType type){
        this.type = type;
        
        if (value instanceof Double){
            if(this.type == DataType.DOUBLE)
                this.value = value;
            else if(this.type == DataType.INTEGER)
                this.value = (double) value;
            else if (this.type == DataType.STRING)
                this.value = String.valueOf(value);
            else
                this.value = null;
        }
        else if (value instanceof Integer){
            if(this.type == DataType.DOUBLE)
            try{
                this.value = (int) value;
            }
            catch (Exception ex){
                System.out.println("Invalid double to integer conversion");
            }
            else if(this.type == DataType.INTEGER)
                this.value = value;
            else if (this.type == DataType.STRING)
                this.value = String.valueOf(value);
            else
                this.value = null;
        }
        else if (value instanceof String) {
            if (this.type == DataType.STRING){
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


    public DataType getType() {
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
                type = DataType.INTEGER;
            else if (value instanceof Double)
                type = DataType.DOUBLE;
            else if (value instanceof String)
                type = DataType.STRING;
            else 
                type = null;
            this.value = value;
        } catch (Exception e) {
            System.out.println(e);
            this.value = null;
        }
    }
}
