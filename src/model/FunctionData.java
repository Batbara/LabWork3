package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FunctionData {
    private Map<Integer, Double> dataMapping;
    public FunctionData(){
        dataMapping = new HashMap<>();
    }
    public void addPair(Integer nValue, Double timeValue){
        dataMapping.put(nValue, timeValue);
    }
    public Map<Integer, Double> getData(){
        return dataMapping;
    }
}
