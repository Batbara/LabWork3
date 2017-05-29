package view;


import model.FunctionData;
import model.RandomArray;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class InfoLogger {
    private static final String fileName = System.getProperty("user.dir")+"/log.txt";
    private File dataLog;
    private static int constructorCalls =0;
    public InfoLogger(){
        constructorCalls++;
        dataLog = new File(fileName);
        if (dataLog.exists()){
            dataLog.delete();
        }
    }

    public static int getConstructorCalls() {
        return constructorCalls;
    }

    public void logData(FunctionData data) throws IOException {
        if (!dataLog.exists()){
            dataLog.createNewFile();
        }
        FileWriter writer = new FileWriter(dataLog, true);
        writer.write("Unsorted:\n");
        for (RandomArray array : data.getArraysToProcess()){
            writer.write(Arrays.toString(array.getRandomArray())+'\n');
        }
        writer.write("Sorted:\n");
        writer.close();
    }
    public void logArray(Integer[] array) throws IOException {
        if (!dataLog.exists()){
            dataLog.createNewFile();
        }
        FileWriter writer = new FileWriter(dataLog,true);

            writer.write(Arrays.toString(array)+'\n');

        writer.close();
    }
}
