package app.core.arrays;

public class ArrayUtil {
    
    @SuppressWarnings("unchecked")
    public static final <E extends Number> E[][] newBidimensionalArray(Class<E> klass, int rows, int columns) {
        E [][] arr = (E [][]) (java.lang.reflect.Array.newInstance(klass, rows, columns));
        return arr;
    }

    public static final <E extends Number> E[][] newBidimensionalArrayAndInit(Class<E> klass, int rows, int columns) {
        E [][] arr = newBidimensionalArray(klass, rows, columns);
        initArray(klass, arr, rows, columns);
        return arr;
    }

    public static final <E extends Number> void initArray(Class<E> klass, E [][] arr, int rows, int columns) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                arr[i][j] = createDefaultNumber(klass);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <E extends Number> E createDefaultNumber(Class<E> klass) {
        if(klass == Byte.class) {
            return (E) Byte.valueOf((byte) 0);
        }else if(klass == Short.class) {
            return (E) Short.valueOf((short)0);
        }else if (klass == Integer.class) {
            return (E) Integer.valueOf(0);
        } else if (klass == Double.class) {
            return (E) Double.valueOf(0.0);
        } else if (klass == Long.class) {
            return (E) Long.valueOf(0L);
        } else if (klass == Float.class) {
            return (E) Float.valueOf(0.0f);
        } else {
            throw new IllegalArgumentException("Unsupported number type: " + klass);
        }
    }

    public static <E extends Number> void multiplyAndAssign(E [][] result, E [][] x, E [][] y, int i, int j, int k) {
        multiplyAndAssign(result, ArrayImpl.only(x), ArrayImpl.only(y), i, j, k);
    }

    @SuppressWarnings("unchecked")
    public static <E extends Number> void multiplyAndAssign(E [][] result, Array<E> x, Array<E> y, int i, int j, int k) {
        Number xNumber = x.currentArray[i][k];
        Number yNumber = y.currentArray[k][j];
        Number partialResult = result[i][j];
        Class<?> klass =  x.type();
        if(klass.isAssignableFrom(Byte.class)) {       
            result[i][j] = (E) toByte(toByte(partialResult) + (toByte(xNumber) * toByte(yNumber)));   
        }else if(klass.isAssignableFrom(Short.class)) {
            result[i][j] = (E) toShort(toShort(partialResult) + (toShort(xNumber) * toShort(yNumber)));
        }else if(klass.isAssignableFrom(Integer.class)) {
            result[i][j] = (E) toInt(toInt(partialResult) + (toInt(xNumber) * toInt(yNumber)));
        }else if(klass.isAssignableFrom(Long.class)) {
            result[i][j] = (E) toLong(toLong(partialResult) + (toLong(xNumber) * toLong(yNumber)));
        }else if(klass.isAssignableFrom(Float.class)) {
            result[i][j] = (E) toFloat(toFloat(partialResult) + (toFloat(xNumber) * toFloat(yNumber)));
        }else if(klass.isAssignableFrom(Double.class))  {
            result[i][j] = (E) toDouble(toDouble(partialResult) + (toDouble(xNumber) * toDouble(yNumber)));
        }else {
            throw new UnsupportedOperationException("La multiplicacion y asignacion aun no esta soportado para la clase [" + klass.getName() + "]");
        }
    }

    private static Byte toByte(Number number) {
        return Byte.valueOf(String.valueOf(number));
    }

    private static Short toShort(Number number) {
        return Short.valueOf(String.valueOf(number));
    }

    private static Integer toInt(Number number) {
        return Integer.valueOf(String.valueOf(number));
    }

    private static Long toLong(Number number) {
        return Long.valueOf(String.valueOf(number));
    }
    
    private static Float toFloat(Number number) {
        return Float.valueOf(String.valueOf(number));
    }

    private static Double toDouble(Number number) {
        return Double.valueOf(String.valueOf(number));
    }
}