package app.core.thread.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import app.core.arrays.Array;
import app.core.arrays.ArrayImpl;
import app.core.arrays.ArrayUtil;
import app.core.arrays.Multiplication;

public class ForkJoinMultiplication<T extends Number> implements Multiplication<T> {

    protected static final short THRESHOLD = 10;
    
    @Override
    public Array<T> execute(Array<T> x, Array<T> y) {
        T [][] result = ArrayUtil.newBidimensionalArray(x.type(), x.current().length, y.current()[0].length);
        MultiplicationTask task = new MultiplicationTask(x, y, result, 0, x.current().length, 0, y.current().length);
        ForkJoinPool.commonPool().invoke(task);
        return ArrayImpl.only(result);
    }

    protected class MultiplicationTask extends RecursiveAction {

        protected final Array<T> x;
        protected final Array<T> y;
        protected final T[][] result;
        protected final int rowStart;
        protected final int rowEnd;
        protected final int colStart;
        protected final int colEnd;

        public MultiplicationTask(Array<T> x, Array<T> y, T[][] result, int rowStart, int rowEnd, int colStart, int colEnd) {
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
            if (rowEnd - rowStart <= THRESHOLD) {
                for (int i = rowStart; i < rowEnd; i++) 
                    for (int j = colStart; j < y.current()[0].length; j++) {
                        result[i][j] = ArrayUtil.createDefaultNumber(x.type());
                        for (int k = colStart; k < y.current()[0].length; k++) 
                            ArrayUtil.multiplyAndAssign(result, x, y, i, j, k);
                    }
                        
            } else {
                callNewTask();    
            }
        }

        protected void callNewTask() {
            int midRow = (rowStart + rowEnd) / 2;
            MultiplicationTask topHalf = new MultiplicationTask(x, y, result, rowStart, midRow, colStart, colEnd);
            MultiplicationTask bottomHalf = new MultiplicationTask(x, y, result, midRow, rowEnd, colStart, colEnd);
            invokeAll(topHalf, bottomHalf);
        }
    }
    
}
