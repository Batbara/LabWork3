package controller;

import model.ArrayPack;
import model.FunctionData;
import model.RandomArray;
import model.TableRow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SortingAlgorithm implements Runnable {

    private ArrayPack packToSort;
    private Integer eachArrayLength;
    private JTable dataTable;
    private FunctionData functionData;
    private Long averageTime;
    private Thread thread;

    public SortingAlgorithm(ArrayPack packToSort, JTable dataTable, FunctionData functionData) {
        this.packToSort = packToSort;
        this.dataTable = dataTable;
        this.functionData = functionData;

        averageTime = 0L;
        eachArrayLength = packToSort.getPackArraysLength();
        thread = new Thread(this, "thread with n = " + eachArrayLength);
    }

    public void run() {


        System.out.println(thread.getName() + " is running!");
        synchronized (packToSort) {
            List<Long> sortingTime = new ArrayList<>();
            for (RandomArray array : packToSort.getArrayPack()) {
                long startingTime = System.nanoTime();
                Integer[] arrayToSort = Arrays.copyOf(array.getRandomArray(), eachArrayLength, Integer[].class);
                sortMerge(arrayToSort);
                long endTime = System.nanoTime();
                long elapsedTime = (endTime - startingTime) / 1000;
                sortingTime.add(elapsedTime);
            }
            averageTime = 0L;
            for (Long time : sortingTime) {
                averageTime += time;
            }
            System.out.println(averageTime);
            System.out.println(sortingTime.size());
            averageTime = averageTime / sortingTime.size();
            System.out.println(averageTime);
            functionData.getPointsList().addPoint(eachArrayLength, averageTime);
            updateTable();
        }
    }

    private synchronized void updateTable() {
        TableRow row = new TableRow(eachArrayLength, averageTime);
        DefaultTableModel tableModel = (DefaultTableModel) dataTable.getModel();
        tableModel.addRow(row.getRow());
        dataTable.repaint();
        dataTable.revalidate();
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
