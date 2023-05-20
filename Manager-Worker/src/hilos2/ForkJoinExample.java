package hilos2;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ForkJoinExample  {

    private final static short THRESHOLD = 20;

    public BigDecimal[][] multiply(BigDecimal [][] x, BigDecimal [][] y) {
        BigDecimal [][] result = new BigDecimal[x.length][x.length];
        ForkJoinPool.commonPool().invoke(new Task(x, y, result, 0, x.length, 0, y.length));
        return result;
    }

    private class Task extends RecursiveAction {

        final BigDecimal [][] x;
        final BigDecimal [][] y;
        final BigDecimal [][] result;
        final int rowStart;
        final int rowEnd;
        final int colStart;
        final int colEnd;

        private Task(BigDecimal [][] x, BigDecimal [][] y, BigDecimal [][] result, int rowStart, int rowEnd, int colStart, int colEnd) {
            this.x = x;
            this.y = y;
            this.result = result;
            this.rowStart = rowStart;
            this.rowEnd = rowEnd;
            this.colStart = colStart;
            this.colEnd = colEnd;
        }

        @Override
        protected void compute() {
            if (rowEnd - rowStart <= THRESHOLD) 
                for (int i = rowStart; i < rowEnd; i++) 
                    for (int j = colStart; j < y[0].length; j++)  {
                        result[i] = new BigDecimal[x.length];
                        BigDecimal tmp = BigDecimal.ZERO;
                        for (int k = colStart; k < y[0].length; k++) 
                            tmp = tmp.add(x[i][k].multiply(y[k][j]));
                        result[i][j] = tmp;
                    }   
            else  {
                int midRow = (rowStart + rowEnd) / 2;
                RecursiveAction topHalf = new Task(x, y, result, rowStart, midRow, colStart, colEnd);
                RecursiveAction bottomHalf = new Task(x, y, result, midRow, rowEnd, colStart, colEnd);
                invokeAll(topHalf, bottomHalf);
            }
        }
    }
}

class BigDecimalArrayRandomGenerator {

    protected final short maxSize;
    
    private final Random random = new SecureRandom();

    public BigDecimalArrayRandomGenerator(short maxSize) { 
        this.maxSize = maxSize; 
    }

    public BigDecimal[][] newArray() {
        BigDecimal [][] numbers = new BigDecimal[maxSize][maxSize];
        for(int i = 0; i < numbers.length; i++) {
            for(int j = 0; j < numbers[i].length; j++) {
                    numbers[i][j] = new BigDecimal(random.nextDouble());
            }
        }
        return numbers;
    }

    public static final void print(BigDecimal [][] numbers) {
        Arrays.stream(numbers)
            .map(row -> Arrays.stream(row)
                            .map(String::valueOf)
                            .toArray(String[]::new))
            .map(Arrays::toString)
            .forEach(System.out::println);
    }

}