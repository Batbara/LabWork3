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
            averageTime = averageTime / sortingTime.size();
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
        return merge(sortMerge(Arrays.copyOfRange(array, 0, middle)),
                sortMerge(Arrays.copyOfRange(array, middle, length)));
    }

    private Integer[] merge(Integer[] firstArray, Integer[] secondArray) {
        int firstLength = firstArray.length, secondLength = secondArray.length;
        int elementFirstArr = 0, elementSecondArr = 0, length = firstLength + secondLength;
        Integer[] result = new Integer[length];
        for (int i = 0; i < length; i++) {
            if (elementSecondArr < secondLength && elementFirstArr < firstLength) {
                if (firstArray[elementFirstArr] > secondArray[elementSecondArr])
                    result[i] = secondArray[elementSecondArr++];
                else result[i] = firstArray[elementFirstArr++];
            } else if (elementSecondArr < secondLength) {
                result[i] = secondArray[elementSecondArr++];
            } else {
                result[i] = firstArray[elementFirstArr++];
            }
        }
        return result;
    }

}
