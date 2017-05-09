package controller;

import model.FunctionData;
import model.TableRow;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class DataController {
    private FunctionData functionData;
    public DataController(){
        functionData = new FunctionData();
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
            case "Количество массивов: ":
            {
                try {
                   functionData.setNumberOfArrays(Integer.parseInt(data));
                }catch (NumberFormatException e){
                    System.err.println("NumberFormatException caught!");
                    break;
                }
                break;
            }
        }
    }
    public void sorting() throws ExecutionException, InterruptedException {
        functionData.runSortingAlgorithm();
    }
    public List<TableRow> getListOfRows(){
        List<TableRow> listOfRows = new ArrayList<>();
        Map<Integer, Long> dataMapping = functionData.getDataMapping();
        Set<Integer> keys = dataMapping.keySet();
        for (Integer key : keys){
            TableRow row = new TableRow(key, dataMapping.get(key));
            listOfRows.add(row);
        }
        return listOfRows;
    }

}
