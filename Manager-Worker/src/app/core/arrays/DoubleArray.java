package app.core.arrays;

import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class DoubleArray extends ArrayImpl<Double> {

    protected DoubleArray(Double [][] array) {
        super(Double.class, array);
    }

    public static ArrayCreator<Double> of(Supplier<Random> supplier) {
        return new DoubleArrayBuilder(supplier.get());
    }

    public static Array<Double> empty() {
        return new DoubleArray(null);
    }
    
    public static DoubleArray only(Double [][] array) {
        return new DoubleArray(array);
    }

}

class DoubleArrayBuilder implements ArrayCreator<Double> {

    private final Optional<Random> randomOptional;
    private Double [][] array = {};

    public DoubleArrayBuilder(Random random) {
        randomOptional = Optional.ofNullable(random);
    }

    @Override
    public ArrayCreator<Double> create(int rows, int columns) {
        array = IntStream.range(0, rows)
                        .mapToObj(i -> IntStream.range(0, columns)
                                                .mapToDouble(j -> value())
                                                .boxed()
                                                .toArray(Double[]::new))
                        .toArray(Double[][]::new);
        return this;
    }

    @Override
    public Array<Double> build() {
        return new DoubleArray(array);
    }

    private Double value() {
        return randomOptional.isPresent() ? randomOptional.get().nextDouble() : 0d;
    }

}
