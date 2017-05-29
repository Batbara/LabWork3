package controller;

import model.*;
import view.Graph;
import view.GraphComponent;
import view.InfoLogger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SortingAlgorithm implements Runnable {

    private List<RandomArray> arraysToSort;
    private JTable dataTable;
    private GraphComponent graphComponent;
    private FunctionData functionData;
    private InfoLogger logger;

    public SortingAlgorithm(List<RandomArray> arraysToSort, JTable dataTable, FunctionData functionData,
                            GraphComponent graphComponent, InfoLogger logger) {
        this.arraysToSort = arraysToSort;
        this.dataTable = dataTable;
        this.functionData = functionData;
        this.graphComponent = graphComponent;
        this.logger = logger;

    }

    public void run() {

        for (RandomArray randomArray : arraysToSort){
            Point newPoint = getSortedData(randomArray);
            functionData.getPointsList().addPoint(newPoint);
            if(SwingUtilities.isEventDispatchThread()) {

                updateTable(newPoint);
                updateGraph(newPoint);
            }
            else{
                SwingUtilities.invokeLater(() -> {

                    updateTable(newPoint);
                    updateGraph(newPoint);
                });
            }
        }


    }

    private Point getSortedData(RandomArray array){
        int SORTING_TIMES = 100;
        Long averageTime = 0L;
        long startingTime = System.nanoTime();

        int arrayLength = array.size();
        Integer[] sortedArray = new Integer[arrayLength];
        for (int sortCount = 0; sortCount<SORTING_TIMES; sortCount++) {

            Integer[] arrayToSort = Arrays.copyOf(array.getRandomArray(), arrayLength, Integer[].class);
            sortedArray=sortMerge(arrayToSort);
        }
        try {
            logger.logArray(sortedArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.nanoTime();
        long elapsedTime = (endTime - startingTime);
        averageTime+=elapsedTime;
        averageTime = averageTime / (SORTING_TIMES*1000);
        return new Point(averageTime,arrayLength);
    }
    private synchronized void updateTable(Point newPoint) {
        TableRow row = new TableRow(newPoint.getNumberOfElements(), newPoint.getTime());
        DefaultTableModel tableModel = (DefaultTableModel) dataTable.getModel();
        tableModel.addRow(row.getRow());
        dataTable.repaint();
        dataTable.revalidate();
    }
    private synchronized void updateGraph(Point newPoint){
        Graph graph = graphComponent.getGraph();
        graph.getGraphPoints().addPoint(newPoint);
        graphComponent.repaint();
    }
    private Integer[] sortMerge(Integer[] array) {
        int length = array.length;
        if (length < 2)
            return array;
        int middle = length / 2;
        Integer[] rightPart = Arrays.copyOfRange(array, 0, middle);
        Integer[] leftPart = Arrays.copyOfRange(array, middle, length);
        return merge(sortMerge(rightPart),
                sortMerge(leftPart));
    }

    private Integer[] merge(Integer[] rightHalf, Integer[] leftHalf) {
        int rightHalfLength = rightHalf.length;
        int leftHalfLength = leftHalf.length;

        int rightHalfPointer = 0, leftHalfPointer = 0, length = rightHalfLength + leftHalfLength;
        Integer[] result = new Integer[length];

        for (int element = 0; element < length; element++) {

            if(!isArrayEnded(leftHalf, leftHalfPointer) &&
                    !isArrayEnded(rightHalf, rightHalfPointer)) {

                int elementFromRightHalf = rightHalf[rightHalfPointer];
                int elementFromLeftHalf = leftHalf[leftHalfPointer];

                if (elementFromRightHalf > elementFromLeftHalf) {
                    result[element] = elementFromLeftHalf;
                    leftHalfPointer=shift(leftHalfPointer);
                }
                else {
                    result[element] = elementFromRightHalf;
                    rightHalfPointer=shift(rightHalfPointer);
                }
            }
            else
                if (!isArrayEnded(leftHalf, leftHalfPointer)) {
                    int elementFromLeftHalf = leftHalf[leftHalfPointer];
                    result[element] = elementFromLeftHalf;
                    leftHalfPointer=shift(leftHalfPointer);
                }
                else {
                    int elementFromRightHalf = rightHalf[rightHalfPointer];
                    result[element] = elementFromRightHalf;
                    rightHalfPointer=shift(rightHalfPointer);
            }
        }
        return result;
    }
    private int shift(int counter){
        return counter+1;
    }
    private boolean isArrayEnded(Integer[]array, int currElementIndex){
        return currElementIndex >= array.length;
    }

}
