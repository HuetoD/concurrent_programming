package app.core.arrays;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Supplier;

public class ArrayImpl<T extends Number> extends Array<T> {
    
    protected ArrayImpl(Class<T> klass, T [][] currentArray) { super(klass, currentArray); }
    
    public static <E extends Number> ArrayCreator<E> of(Class<E> klass, Supplier<E> randomNumberGeneratorSupplier) {
        return new ArrayBuilder<E>(klass, randomNumberGeneratorSupplier);
    }
    
    @SuppressWarnings("unchecked")
    public static <E extends Number> Array<E> only(E [][] array) {
        return new ArrayImpl<E>((Class<E>) array[0].getClass().getComponentType(), array);
    }

    public static <E extends Number> Array<E> empty(Class<E> klass) {
        return new ArrayImpl<E>(klass, ArrayUtil.newBidimensionalArray(klass, 0, 0));
    }

    @Override
    public void print() {
        Arrays.stream(currentArray)
                .map(row -> Arrays.stream(row)
                                .map(String::valueOf)
                                .toArray(String[]::new))
                .map(Arrays::toString)
                .forEach(System.out::println);
    }
    
    @Override
    public <E extends Number> Array<E> rebuild(Class<E> klass, Supplier<E> numberGeneratorSupplier, int rows, int columns) {
        return of(klass, numberGeneratorSupplier).create(rows, columns).build();
    }

    @Override
    public boolean equals(Object other) {
        return this == other && compare(other);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(currentArray);
    }

    @Override
    public boolean compare(Object other) {
        return other != null && other instanceof Array && Arrays.deepEquals(currentArray, Array.class.cast(other).current());
    }
    
}

class ArrayBuilder<T extends Number> implements ArrayCreator<T> {
        
    private final Class<T> klass;
    private final Supplier<T> randomNumberGeneratorSupplier;
    private T [][] currentArray;
    
    public ArrayBuilder(Class<T> klass, Supplier<T> randomNumberGeneratorSupplier) { 
        this.klass = klass;
        this.randomNumberGeneratorSupplier = randomNumberGeneratorSupplier;
    }
    
    @Override
    public ArrayBuilder<T> create(int rows, int columns) {
        currentArray = ArrayUtil.newBidimensionalArray(klass, rows, columns);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                currentArray[i][j] = randomNumberGeneratorSupplier.get();
            }
        }

        return this;
    }
    
    @Override
    public Array<T> build() {
        return new ArrayImpl<T>(klass, currentArray);
    }
} 
