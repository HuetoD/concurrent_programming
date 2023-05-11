package hilos2;

import static hilos2.E5_MatrizUtileria.*;

public class E5_Manager {

    private Thread poolWorkers[];

    public int[][] multiplicarConcurrente(int A[][], int B[][]) {
        
        validarAxB(A[0].length, B.length);
       
        
        int filasA = A.length;
        int columnasB = B[0].length;
        int C[][] = new int[filasA][columnasB];
        
        this.poolWorkers = new Thread[filasA];
        
       
        for (int i = 0; i < filasA; i++) {
            this.poolWorkers[i] = new Thread(new E5_Worker(C, A, B, i));
            this.poolWorkers[i].start();
        }
          
        
        for (int i = 0; i < filasA; i++) {
            try {
                this.poolWorkers[i].join();
            } catch (InterruptedException e) {
            }
        }

        return C;
    }
    
}
