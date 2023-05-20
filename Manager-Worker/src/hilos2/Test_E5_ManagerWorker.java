package hilos2;

import java.math.BigDecimal;
import java.util.Date;
import static hilos2.E5_MatrizUtileria.*;

public class Test_E5_ManagerWorker {

    private static final BigDecimalArrayRandomGenerator BIG_DECIMAL_ARRAY_RANDOM_GENERATOR = new BigDecimalArrayRandomGenerator((short) 1000);
    private static final BigDecimal [][] X;
    private static final BigDecimal [][] Y;

    static {
        X = BIG_DECIMAL_ARRAY_RANDOM_GENERATOR.newArray();
        Y = BIG_DECIMAL_ARRAY_RANDOM_GENERATOR.newArray();
    }

    public static void main(String[] args) {
        BigDecimal [][] result;
        int A[][], B[][], C[][], D[][];
        double tiempoSerial, tiempoConcurrente;
        double speedUp;

        Date tiempoInicialSerial, tiempoFinalSerial, 
            tiempoInicialConcurrente, tiempoFinalConcurrente,
            tiempoInicialForkJoin, tiempoFinalForkJoin;
       
        
        A = E5_Matriz.crear(1000, 1000);
        B = E5_Matriz.crear(1000, 1000);

        E5_Manager m = new E5_Manager();

        //Descomente si se desea aburrir esperando a que imprima
        // E5_Matriz.imprimir(A);
        // E5_Matriz.imprimir(B);
        
        tiempoInicialSerial = new Date();
        C = multiplicarSerial(A, B);
        tiempoFinalSerial = new Date();
        
        //Descomente si se desea aburrir esperando a que imprima 
        // E5_Matriz.imprimir(C);
        tiempoInicialConcurrente = new Date();
        D = m.multiplicarConcurrente(A, B);
        tiempoFinalConcurrente = new Date();

        tiempoInicialForkJoin = new Date();
        result = new ForkJoinExample().multiply(X, Y);
        tiempoFinalForkJoin = new Date();
        //BigDecimalArrayRandomGenerator.print(result);

        System.out.printf("Matriz A: (%d,%d) \n", A.length, A[0].length);

        System.out.printf("Matriz B: (%d,%d) \n", B.length, B[0].length);

        System.out.printf("Matriz C: (%d,%d) \n", C.length, C[0].length);

        tiempoSerial = tiempoFinalSerial.getTime() - tiempoInicialSerial.getTime();
        tiempoConcurrente = tiempoFinalConcurrente.getTime() - tiempoInicialConcurrente.getTime();
        double tiempoForkJoin = tiempoFinalForkJoin.getTime() - tiempoInicialForkJoin.getTime();

        speedUp = calcularSpeedUp(tiempoSerial, tiempoConcurrente);
        System.out.printf("\n Tiempo serial: %,.6f ms ", tiempoSerial);        
        System.out.printf("\n Tiempo concurrente: %,.6f ms ", tiempoConcurrente);
        System.out.printf("\n Tiempo fork_join: %,.6f ms ", tiempoForkJoin);
        System.out.printf("\n SpeedUp [tiempoSerial | tiempoConcurrente]: %.6f\n", speedUp);
        System.out.printf("SpeedUp [tiempoConcurrente | tiempoForkJoin]: %.6f  \n\n", calcularSpeedUp(tiempoConcurrente, tiempoForkJoin));

    }

    private static double calcularSpeedUp(double s, double p) {
        double r;
        r = s / p;
        return r;
    }

}
