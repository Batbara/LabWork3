package controller;

import model.FunctionData;
import model.PointsList;
import model.RandomArray;
import view.GraphComponent;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DataController {
    private FunctionData functionData;
    private JTable dataTable;
    private GraphComponent graphComponent;

    public DataController() {
        functionData = new FunctionData();
        dataTable = new JTable();
        graphComponent = new GraphComponent();

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

    public void sorting() throws ExecutionException, InterruptedException {
        setPointsList(new PointsList());
        List<RandomArray> arraysToProcess = functionData.getArraysToProcess();

        SortingAlgorithm algorithm = new SortingAlgorithm(arraysToProcess, dataTable, functionData, graphComponent);
        Thread processing = new Thread(algorithm);
        processing.start();
    }

    public void setPointsList(PointsList pointsList) {
        functionData.getPointsList().setPointsList(pointsList);
    }

    public void setDataTable(JTable dataTable) {
        this.dataTable = dataTable;
    }

    public void setGraphComponent(GraphComponent graphComponent) {
        this.graphComponent = graphComponent;
    }
}
