package model;

import java.util.ArrayList;
import java.util.List;

public class FunctionData {
    private int increasingStep;
    private int maxArrayLength;
    private PointsList pointsList;
    private List<RandomArray> arraysToProcess;

    public FunctionData() {
        maxArrayLength = 0;
        increasingStep = 0;
        pointsList = new PointsList();

        arraysToProcess = new ArrayList<>();
    }

    public void createArrays() {
        arraysToProcess = new ArrayList<>();
        int MIN_ARRAY_LENGTH = 2;
        for (int array = MIN_ARRAY_LENGTH; array < maxArrayLength; array += increasingStep) {
            RandomArray randomArray = new RandomArray(array);
            arraysToProcess.add(randomArray);
        }
    }

    public void setIncreasingStep(int increasingStep) {
        this.increasingStep = increasingStep;
    }

    public void setMaxArrayLength(int maxArrayLength) {
        this.maxArrayLength = maxArrayLength;
    }


    public PointsList getPointsList() {
        return pointsList;
    }

    public List<RandomArray> getArraysToProcess() {
        return arraysToProcess;
    }
}
