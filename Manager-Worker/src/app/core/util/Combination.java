package app.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Combination<T> {
    
    public List<List<T>> getCombinations(T [] objects) {
        List<List<T>> combinations = new ArrayList<>();
        for (int i = 0; i < objects.length; i++) {
            for (int j = 0; j < objects.length; j++) {
                if (i != j) {
                    combinations.add(Arrays.asList(objects[i], objects[j]));
                }
            }
        }
        return combinations;
    }

}
