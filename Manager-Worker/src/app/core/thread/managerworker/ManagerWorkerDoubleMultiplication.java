package app.core.thread.managerworker;

import app.core.arrays.Array;
import app.core.arrays.DoubleArray;

public final class ManagerWorkerDoubleMultiplication extends ManagerWorkerMultiplication<Double> {

    @Override
    public Array<Double> execute(Array<Double> x, Array<Double> y) {
        return DoubleArray.only(new Manager(DoubleWorker::new , x.type()).multiply(x.current(), y.current()));
    }    
    
    protected class DoubleWorker extends ManagerWorkerMultiplication<Double>.Worker {

        protected DoubleWorker(Double [][] x, Double [][] y, Double [][] z, int row) { super(x, y, z, row); }

        @Override
        public void run() {
            for (int i = 0; i < y[0].length; i++) 
                for (int j = 0; j < x[row].length; j++)
                    z[row][i] += x[row][j] * y[j][i];
        }

    }

}
