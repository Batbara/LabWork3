package controller;



import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

public class DataController {
    private FunctionData functionData;
    private JTable dataTable;
    public DataController(){
        functionData = new FunctionData();
        dataTable = new JTable();
    }
    public void createArrays(){
        functionData.createArrays();
    }
    public void setByKey(String key, String data){
        switch (key){
            case "Max количество элементов:":
            {
                try {
                    functionData.setMaxArrayLength(Integer.parseInt(data));
                }catch (NumberFormatException e){
                    System.err.println("NumberFormatException caught!");
                    break;
                }
                break;
            }
            case "Шаг:":
            {
                try {
                   functionData.setIncreasingStep(Integer.parseInt(data));
                }catch (NumberFormatException e){
                    System.err.println("NumberFormatException caught!");
                    break;
                }
                break;
            }
        }
    }
    public void sorting() throws ExecutionException, InterruptedException {

        List<ArrayPack> arraysToProccess = functionData.getArraysToProccess();
        for (ArrayPack arrayPack : arraysToProccess) {

            SortingAlgorithm startAlgo = new SortingAlgorithm(arrayPack, dataTable, functionData);
            startAlgo.getThread().join(100, 10);

        }

    }
//   public void updateDataMapping(){
//            DefaultTableModel tableModel = (DefaultTableModel) dataTable.getModel();
//            Vector<Vector<String>> allData = tableModel.getDataVector();
//            for(Vector<String> rowData : allData){
//                Integer arrayLength = Integer.parseInt(rowData.get(0));
//                Long timeValue = Long.parseLong(rowData.get(1));
//                functionData.getDataMapping().put(arrayLength, timeValue);
//            }
//    }

    public Map<Integer,Long> getData(){
        return functionData.getDataMapping();
    }
    public int getStep(){
        return functionData.getIncreasingStep();
    }
    public int getMaxArrayLength(){
        return functionData.getMaxArrayLength();
    }

    public void setDataTable(JTable dataTable) {
        this.dataTable = dataTable;
    }
}
