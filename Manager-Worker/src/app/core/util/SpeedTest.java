package app.core.util;

import java.util.List;

public final class SpeedTest {

    private final long init;
    private final long end;

    private String testName = super.toString();
    
    protected SpeedTest(long init, long end) {
        this.init = init;
        this.end = end;
    }
    
    public static SpeedTestBuilder start() {
        return new SpeedTestBuilder();
    }
    
    public SpeedTest resume(String testName) {
        this.testName = testName;
        System.out.printf("[%s]: %dms\n", testName, end - init);
        return this;
    }
    
    public double calculateSpeedUp(SpeedTest anotherTest) {
        double diff;
        if(anotherTest == null || (diff = anotherTest.init - anotherTest.end) == 0)
            throw new IllegalArgumentException("No es posible calcular el speed up, posible valor nulo o denominador con 0");
        return (init - end) / diff;
    }
    
    public static void printSpeedUpResume(SpeedTest... tests) {
        List<List<SpeedTest>> combinations = new Combination<SpeedTest>().getCombinations(tests);
        for(final List<SpeedTest> combination : combinations) {
            for(int i = 0; i < combination.size() - 1; i++) {
                SpeedTest one = combination.get(i);
                SpeedTest two = combination.get(i + 1);
                System.out.printf("SPEED_UP [%s/%s] = %.6fms\n", one.testName, two.testName, one.calculateSpeedUp(two));
            }
        }
    }

}