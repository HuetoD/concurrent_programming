package app;

import app.core.arrays.Array;
import app.core.arrays.DoubleArray;
import app.core.arrays.MultiplicationType;
import app.core.util.MultiplicationCreator;
import app.core.util.SpeedTest;

import static app.core.arrays.MultiplicationType.FORK_JOIN;
import static app.core.arrays.MultiplicationType.MANAGER_WORKER;
import static app.core.arrays.MultiplicationType.SINGLE_THREAD;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class Main {
    
    static final short ROWS = 1000;
    static final short COLUMNS = 1000;

    
    static Array<Double> first = DoubleArray.empty();
    static Array<Double> second = DoubleArray.empty();

    static Map<MultiplicationType, Array<Double>> results = new HashMap<>(MultiplicationType.values().length);

    static MultiplicationType currentMultiplyType = null;

    public static void main(String[] args) {
        System.out.print("Init arrays... ");
        initArray(first);
        System.out.print("50%... ");
        initArray(second);
        System.out.println("done!\n");
        
        SpeedTest forkJoinTest = buildMultiplyTest(FORK_JOIN, "Time with fork_join");
        SpeedTest managerWorkerTest = buildMultiplyTest(MANAGER_WORKER, "Time with manager_worker");
        SpeedTest singleThreadTest = buildMultiplyTest(SINGLE_THREAD, "Time with single_thread");

        System.out.println();
        SpeedTest.printSpeedUpResume(singleThreadTest, managerWorkerTest, forkJoinTest);

        //Descomentar si se desea imprimir las matrices
        // System.out.printf("\n\n[%s]:\n", FORK_JOIN);
        // results.get(FORK_JOIN).print();
        // System.out.println("-------------");
        // System.out.printf("\n[%s]:\n", MANAGER_WORKER);
        // results.get(MANAGER_WORKER).print();
        // System.out.println("-------------");
        // System.out.printf("\n[%s]:\n", SINGLE_THREAD);
        // results.get(SINGLE_THREAD).print();
    }
    
    static SpeedTest buildMultiplyTest(MultiplicationType type, String resume) {
        currentMultiplyType = type;
        return SpeedTest.start()
                        .print("Executing heavy task with [" + type.name() + "]... ")
                        .execute(Main::multiply)
                        .end()
                        .resume(resume);
    }

    static void initArray(Array<Double> array) {
        array.assign(DoubleArray.of(SecureRandom::new)
                            .create(ROWS, COLUMNS)
                            .build());
    }

    static void multiply() {
        results.put(currentMultiplyType, MultiplicationCreator.of(currentMultiplyType, Double.class).execute(first, second));
    }
    
}
