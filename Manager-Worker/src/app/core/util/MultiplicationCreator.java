package app.core.util;

import app.core.arrays.Multiplication;
import app.core.arrays.MultiplicationType;
import app.core.thread.forkjoin.ForkJoinDoubleMultiplication;
import app.core.thread.forkjoin.ForkJoinMultiplication;
import app.core.thread.managerworker.ManagerWorkerDoubleMultiplication;
import app.core.thread.managerworker.ManagerWorkerMultiplication;
import app.core.thread.singlethreaded.SingleThreadedMultiplication;

public final class MultiplicationCreator {
    
    /**
     * <p>Define el tipo de implementacion a usar, dependiendo del recurso a usar y
     * el tipo de dato a multiplicar.</p><br>
     * 
     * <p>Usando genericos se obtiene mayor libertad para escoger cualquier tipo de dato
     * que herede de Number, a cambio de ello, se pierde rendimiento. Por lo que se 
     * han implementado dos clases mas que han sido optimizados para el tipo Double.
     * Estas clases son:</p><br>
     * 
     * <p>{@link ManagerWorkerDoubleMultiplication}</p>
     * <p>{@link ForkJoinDoubleMultiplication}</p>
     * 
     * 
     * @param <T> Generico que hereda de Number (byte, short, int, long, float, double)
     * @param type Tipo de recurso de multiplicacion (serial, manager-worker, fork-join)
     * @param klass Tipo de clase que se va a multiplicar
     * @return Implementacion de Multiplication
     */
    @SuppressWarnings("unchecked")
    public static <T extends Number> Multiplication<T> of(MultiplicationType type, Class<T> klass) {
        boolean isDouble = klass == Double.class;
        switch(type) {
            case SINGLE_THREAD:
                return new SingleThreadedMultiplication<T>();
            case MANAGER_WORKER:
                return isDouble ? (Multiplication<T>) new ManagerWorkerDoubleMultiplication() : new  ManagerWorkerMultiplication<T>();
            case FORK_JOIN:
                return isDouble ? (Multiplication<T>) new ForkJoinDoubleMultiplication() : new ForkJoinMultiplication<T>();
            default:
                throw new IllegalArgumentException("No existe una implentacion para: " + type);
        }
    }
    
}
