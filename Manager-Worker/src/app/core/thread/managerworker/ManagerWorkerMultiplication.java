package app.core.thread.managerworker;

import app.core.arrays.Array;
import app.core.arrays.ArrayImpl;
import app.core.arrays.ArrayUtil;
import app.core.arrays.Multiplication;

public class ManagerWorkerMultiplication<T extends Number> implements Multiplication<T> {

    @Override
    public Array<T> execute(Array<T> x, Array<T> y) {
        return ArrayImpl.only(new Manager(Worker::new , x.type()).multiply(x.current(), y.current()));
    }
    
    protected class Manager {
        
        protected final WorkerSupplier<T> supplier;

        protected final Class<T> type;

        protected Thread [] poolWorkers;

        protected Manager(WorkerSupplier<T> supplier, Class<T> type) {
            this.supplier = supplier;
            this.type = type;
        }
        
        protected T[][] multiply(T [][] x, T [][] y) {
            T [][] z = ArrayUtil.newBidimensionalArrayAndInit(type, x.length, y[0].length);
            poolWorkers = new Thread[z.length];
            
            for (int i = 0; i < z.length; i++) {
                Thread thread = poolWorkers[i] = new Thread(supplier.get(x, y, z, i));
                thread.start();
            }
            
            for (int i = 0; i < z.length; i++) {
                try {
                    poolWorkers[i].join();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
            
            return z;
        }
        
    }
    
    protected class Worker implements Runnable {

        protected final T x[][];
        protected final T y[][];
        protected final T z[][];
        protected final int row;
        
        protected Worker(T [][] x, T [][] y, T [][] z, int row) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.row = row;
        }
        
        @Override
        public void run() {
            for (int i = 0; i < y[0].length; i++) 
                for (int j = 0; j < x[row].length; j++)
                    ArrayUtil.multiplyAndAssign(z, x, y, row, i, j);
        }
        
    }

    interface WorkerSupplier<R extends Number> {

        ManagerWorkerMultiplication<R>.Worker get(R [][] x, R [][] y, R [][] z, int row);

    }
    
}
