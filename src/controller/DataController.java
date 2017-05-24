package controller;

import model.FunctionData;
import model.PointsList;
import model.RandomArray;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DataController {
    private FunctionData functionData;
    private JTable dataTable;

    public DataController() {
        functionData = new FunctionData();
        dataTable = new JTable();

    }

    public void createArrays() {
        functionData.createArrays();
    }

    public void setByKey(String key, String data) {
        switch (key) {
            case "Max количество элементов:": {
                try {
                    functionData.setMaxArrayLength(Integer.parseInt(data));
                } catch (NumberFormatException e) {
                    System.err.println("NumberFormatException caught!");
                    break;
                }
                break;
            }
            case "Шаг:": {
                try {
                    functionData.setIncreasingStep(Integer.parseInt(data));
                } catch (NumberFormatException e) {
                    System.err.println("NumberFormatException caught!");
                    break;
                }
                break;
            }
        }
    }

    public boolean sorting() throws ExecutionException, InterruptedException {
        setPointsList(new PointsList());
        List<RandomArray> arraysToProcess = functionData.getArraysToProcess();

        System.out.println("num of threads: " + arraysToProcess.size());
        ExecutorService threadPool = Executors.newFixedThreadPool(arraysToProcess.size());
        for (RandomArray arrayPack : arraysToProcess) {

            threadPool.submit(new SortingAlgorithm(arrayPack, dataTable, functionData));
        }
        threadPool.shutdown();
        boolean finished = threadPool.awaitTermination(500, TimeUnit.DAYS);
        return finished;
    }


    public PointsList getData() {
        return functionData.getPointsList();
    }

    public void setPointsList(PointsList pointsList) {
        functionData.getPointsList().setPointsList(pointsList);
    }

    public void setDataTable(JTable dataTable) {
        this.dataTable = dataTable;
    }

}
