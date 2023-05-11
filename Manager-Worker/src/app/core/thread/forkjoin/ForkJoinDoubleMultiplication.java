package app.core.thread.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import app.core.arrays.Array;
import app.core.arrays.DoubleArray;

public final class ForkJoinDoubleMultiplication extends ForkJoinMultiplication<Double> {

    @Override
    public Array<Double> execute(Array<Double> x, Array<Double> y) {
        Double [][] result = new Double[x.current().length][y.current()[0].length];
        RecursiveAction task = new DoubleMultiplicationTask(x, y, result, 0, x.current().length, 0, y.current().length);
        ForkJoinPool.commonPool().invoke(task);
        return DoubleArray.only(result);
    }

    private class DoubleMultiplicationTask extends ForkJoinMultiplication<Double>.MultiplicationTask {

        private final Double [][] xArray;
        private final Double [][] yArray;

        protected DoubleMultiplicationTask(Array<Double> x, Array<Double> y, Double [][] result, int rowStart, int rowEnd, int colStart, int colEnd) {
            super(x, y, result, rowStart, rowEnd, colStart, colEnd);
            this.xArray = x.current();
            this.yArray = y.current();
        }

        @Override
        protected void compute() {
            if (rowEnd - rowStart <= THRESHOLD) 
                for (int i = rowStart; i < rowEnd; i++) 
                    for (int j = colStart; j < yArray[0].length; j++)  {
                        double temp = 0d;
                        for (int k = colStart; k < yArray[0].length; k++) 
                            temp += xArray[i][k] * yArray[k][j];
                        result[i][j] = temp;
                    }   
            else 
                callNewTask();    
        }

        @Override
        protected void callNewTask() {
            int midRow = (rowStart + rowEnd) / 2;
            RecursiveAction topHalf = new DoubleMultiplicationTask(x, y, result, rowStart, midRow, colStart, colEnd);
            RecursiveAction bottomHalf = new DoubleMultiplicationTask(x, y, result, midRow, rowEnd, colStart, colEnd);
            invokeAll(topHalf, bottomHalf);
        }

    }
    
}
