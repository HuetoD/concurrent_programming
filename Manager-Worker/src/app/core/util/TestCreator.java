package app.core.util;

public interface TestCreator<E extends TestCreator<E>> {
        
        E print(String info);
        
        E execute(SpeedAction action);

        SpeedTest end();
}
