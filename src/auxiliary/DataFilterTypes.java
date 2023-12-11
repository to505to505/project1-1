package auxiliary;

public interface DataFilterTypes {

    public Double[] selectRow (Double[][] inputData, int row);
    public Double[][] selectRows (Double[][] inputData, int[] rows);

    public Double[] selectColumn (Double[][] inputData, int column);
    public Double[][] selectColums (Double[][] inputData, int[] columns);


};
