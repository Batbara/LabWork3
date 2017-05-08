package controller;

import model.FunctionData;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataController {
    private FunctionData data;
    private int maxArrayLength;
    private int arrayQuantity;
    private List< List<Integer> > arraysToProccess;
    public DataController(){
        data = new FunctionData();
        arraysToProccess = new ArrayList<>();
        maxArrayLength = 0;
        arrayQuantity = 0;
        createArrays();
    }
    public void createArrays(){
        Random randomGenerator = new Random();
        for (int array=0; array<arrayQuantity; array++){
            int length = randomGenerator.nextInt(maxArrayLength);
            List<Integer> randomArray = new ArrayList<>();
            for (int size=0; size<length; size++){
                randomArray.add(randomGenerator.nextInt());
            }
            arraysToProccess.add(randomArray);
        }
    }
    public void setByKey(String key, String data){
        switch (key){
            case "Max количество элементов:":
            {
                try {
                    maxArrayLength = Integer.parseInt(data);
                }catch (NumberFormatException e){
                    System.err.println("NumberFormatException caught!");
                    break;
                }
                break;
            }
            case "Количество массивов: ":
            {
                try {
                    arrayQuantity = Integer.parseInt(data);
                }catch (NumberFormatException e){
                    System.err.println("NumberFormatException caught!");
                    break;
                }
                break;
            }
        }
    }
}
