package app.core.arrays;

import java.util.function.Supplier;

public abstract class Array<T extends Number> {
    
    protected Class<T> type;

    protected T [][] currentArray;

    protected Array(Class<T> type, T [][] currentArray) {
        this.type = type;
        this.currentArray = currentArray;
    }

    public abstract void print();
    
    public abstract <E extends Number> Array<E> rebuild(Class<E> klass, Supplier<E> numberGeneratorSupplier, int rows, int columns);

    public abstract boolean compare(Object other);

    public Class<T> type() { return type; }

    public T [][] current() { return currentArray; }

    public void assign(Array<T> another) {
        currentArray = another.current();
        type = another.type;
    }
}
