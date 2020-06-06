import java.text.DecimalFormat;
import java.util.stream.IntStream;

public class Matrix {
    private double values[][];

    public Matrix(double values[][]) {
        this.values = new double[values.length][values[0].length];
        IntStream.range(0, this.values.length).forEach(r ->
                IntStream.range(0, this.values[0].length).forEach(col -> this.values[r][col] = values[r][col]));
    }

    public Matrix(int r, int c) {
        values = new double[r][c];
    }

    public Matrix ZeroDiagonal() {
        double ret[][] = new double[values.length][values[0].length];
        IntStream.range(0, values.length).forEach(r ->
                IntStream.range(0, values[0].length).forEach(col -> {
                    if(r == col) {
                        ret[r][col] = 0;
                    } else {
                        ret[r][col] = values[r][col];
                    }
                }));
        return new Matrix(ret);
    }

    public Matrix applySignFunction() {
        double ret[][] = new double[values.length][values[0].length];
        IntStream.range(0, values.length).forEach(r ->
                IntStream.range(0, values[0].length).forEach(col -> {
                    if(values[r][col] > 0) {
                        ret[r][col] = 1;
                    } else if (values[r][col] < 0) {
                        ret[r][col] = -1;
                    } else {
                        ret[r][col] = 0;
                    }
                }));
        return new Matrix(ret);
    }

    public boolean converged(Matrix other) {
        for(int i = 0; i < values.length ; i++){
            for(int j = 0; j < values[0].length ; j++){
                if(values[i][j] != other.values[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public Matrix multiplyByScalar(double x) {
        double ret[][] = new double[values.length][values[0].length];
        for(int i = 0; i < values.length ; i++){
            for(int j = 0; j < values[0].length ; j++){
                ret[i][j] = x*values[i][j];
            }
        }
        return new Matrix(ret);
    }

    public double[][] getValuesMatrix() {
        return values;
    }

    public Matrix multiply(Matrix matrix) throws Exception {
        if (matrix.values.length != values[0].length ) throw new Exception("Wrong matrixes dimensions to multipy");
        double ret[][] = new double[values.length][matrix.values[0].length];
        IntStream.range(0, values.length).forEach(r ->
                IntStream.range(0, matrix.values[0].length).forEach(col -> {
                    double result = 0;
                    for (int i = 0; i < values[0].length; i++) result += values[r][i] * matrix.values[i][col];
                    ret[r][col] = result;
                }));
        return new Matrix(ret);
    }

    public Matrix transpose() {
        double[][] ret = new double[values[0].length][values.length];
        IntStream.range(0,  values.length).forEach(r ->
                IntStream.range(0, values[0].length).forEach(col -> ret[col][r] = values[r][col]));
        return new Matrix(ret);
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("\n");
        IntStream.range(0, values.length).forEach(r -> {
            IntStream.range(0, values[0].length).forEach(col -> {
                DecimalFormat df = new DecimalFormat("#.00");
                String str = df.format(values[r][col]);
                if (values[r][col] >= 0) buffer.append("   "+  str);
                else  buffer.append("  "+  str);
            });
            buffer.append("\n");
        });
        return buffer.toString();
    }
}