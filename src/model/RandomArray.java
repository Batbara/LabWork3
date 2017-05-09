package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class RandomArray {
    private int maxArrayLength;
    private List<Integer> randomArray;
    private long timeToSort;
    public RandomArray(int n){
        maxArrayLength = n;
        randomArray = new ArrayList<>(maxArrayLength);
        timeToSort = 0;
        createArray();
    }
    private void createArray(){
        Random randomGenerator = new Random();
        int length = randomGenerator.nextInt(maxArrayLength);
        for (int size=0; size<length; size++){
            randomArray.add(randomGenerator.nextInt());
        }
    }
    public int size(){
        return randomArray.size();
    }

    public Object[] getRandomArray() {
        return randomArray.toArray();
    }

    public void setTimeToSort(long timeToSort) {
        this.timeToSort = timeToSort;
    }

    public long getTimeToSort() {
        return timeToSort;
    }
}
