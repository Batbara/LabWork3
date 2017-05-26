package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class RandomArray {
    private int arrayLength;
    private List<Integer> randomArray;

    public RandomArray(int arrayLength) {
        this.arrayLength = arrayLength;
        randomArray = new ArrayList<>(this.arrayLength);
        fillArray();
    }

    private void fillArray() {
        Random randomGenerator = Randomizer.getInstance().getRandomGenerator();
        for (int size = 0; size < arrayLength; size++) {
            randomArray.add(randomGenerator.nextInt());
        }
    }

    public int size() {
        return randomArray.size();
    }

    public Object[] getRandomArray() {
        return randomArray.toArray();
    }
}
