package model;

import java.util.*;
import java.util.concurrent.*;

public class FunctionData {
    private int numberOfArrays;
    private int maxArrayLength;
    private Map<Integer, Long> dataMapping;
    private List< RandomArray > arraysToProccess;

    public FunctionData(){
        maxArrayLength = 0;
        numberOfArrays = 0;
        dataMapping = new HashMap<>();
        arraysToProccess =  new ArrayList<>();
    }
    public void createArrays(){
        arraysToProccess = new ArrayList<>();
        for (int array = 0; array< numberOfArrays; array++){
            RandomArray randomArray = new RandomArray(maxArrayLength);
            arraysToProccess.add(randomArray);
        }
    }

    public void setNumberOfArrays(int numberOfArrays) {
        this.numberOfArrays = numberOfArrays;
    }

    public void setMaxArrayLength(int maxArrayLength) {
        this.maxArrayLength = maxArrayLength;
    }
    public void runSortingAlgorithm() throws ExecutionException, InterruptedException {
        dataMapping = new HashMap<>();
        for (RandomArray array : arraysToProccess){
            ExecutorService threadPool = Executors.newFixedThreadPool(array.size());
            Callable<Long> callable = new SortingAlgorithm(array);
            Future<Long> future = threadPool.submit(callable);
            dataMapping.put(array.size(), future.get());
        }
        Set<Integer> keys = dataMapping.keySet();
        System.out.println("num of arrays: "+arraysToProccess.size());
        for (Integer key : keys){

            System.out.println("n "+key);
            System.out.println("time "+dataMapping.get(key));
            System.out.println("=========");
        }
    }

    public Map<Integer, Long> getDataMapping (){
        return dataMapping;
    }
}
