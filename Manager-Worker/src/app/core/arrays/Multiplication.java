package app.core.arrays;

public interface Multiplication<T extends Number> {
    
    Array<T> execute(Array<T> x, Array<T> y);

    default Array<T> multiply(Array<T> x, Array<T> y) {
        validate(x.current()[0].length, y.current().length);
        return execute(x, y);
    }
    
    default void validate(int xLengthArray, int yLengthArray) {
        if(xLengthArray != yLengthArray)
            throw new UnsupportedOperationException("Diferente tipo de longitud [" + xLengthArray + "!=" + yLengthArray + "]");
    }
    
}
