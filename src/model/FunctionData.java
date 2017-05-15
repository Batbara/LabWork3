package model;

import java.util.*;
import java.util.concurrent.*;

public class FunctionData {
    private int increasingStep;
    private int maxArrayLength;
    private static int MIN_ARRAY_LENGTH = 2;
    private static int NUMBER_OF_ARRAYS = 250;
    private Map<Integer, Long> dataMapping;
    private List< ArrayPack > arraysToProccess;

    public FunctionData(){
        maxArrayLength = 0;
        increasingStep = 0;
        dataMapping = new HashMap<>();
        arraysToProccess =  new ArrayList<>();
    }
    public void createArrays(){
        arraysToProccess = new ArrayList<>();
        for (int array = 2; array< maxArrayLength; array+=increasingStep){
            ArrayPack arrayPack = new ArrayPack(array);
            arraysToProccess.add(arrayPack);
        }
    }

    public void setIncreasingStep(int increasingStep) {
        this.increasingStep = increasingStep;
    }

    public void setMaxArrayLength(int maxArrayLength) {
        this.maxArrayLength = maxArrayLength;
    }
    public void runSortingAlgorithm() throws ExecutionException, InterruptedException {


    }

    public Map<Integer, Long> getDataMapping (){
        return dataMapping;
    }

    public List<ArrayPack> getArraysToProccess() {
        return arraysToProccess;
    }

    public int getIncreasingStep() {
        return increasingStep;
    }

    public int getMaxArrayLength() {
        return maxArrayLength;
    }
}
