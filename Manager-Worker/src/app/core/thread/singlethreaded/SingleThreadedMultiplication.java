package app.core.thread.singlethreaded;

import app.core.arrays.Array;
import app.core.arrays.ArrayImpl;
import app.core.arrays.ArrayUtil;
import app.core.arrays.Multiplication;

public class SingleThreadedMultiplication<T extends Number> implements Multiplication<T> {

    @Override
    public Array<T> execute(Array<T> x, Array<T> y) {
        final T [][] xArray = x.current();
        final T [][] yArray = y.current();
        final T [][] result = ArrayUtil.newBidimensionalArray(y.type(), xArray.length, yArray[0].length);
        for(int i = 0; i < result.length; i++) 
            for(int j = 0; j < yArray[0].length; j++) {
                result[i][j] = ArrayUtil.createDefaultNumber(x.type());
                for(int k = 0; k < result[i].length; k++) 
                    ArrayUtil.multiplyAndAssign(result, xArray, yArray, i, j, k);
            }
                
        return ArrayImpl.only(result);
    }
    
}
