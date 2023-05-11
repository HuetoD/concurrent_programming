package app.core.arrays;

public interface ArrayCreator<T extends Number> {
 
    ArrayCreator<T> create(int rows, int columns);

    Array<T> build();
    
}
