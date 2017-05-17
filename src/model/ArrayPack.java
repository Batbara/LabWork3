package model;

import java.util.ArrayList;
import java.util.List;

public class ArrayPack {
    private int arrayLength;
    private List<RandomArray> arrayPack;
    public ArrayPack(int arrayLength){
        this.arrayLength = arrayLength;
        arrayPack = new ArrayList<>(arrayLength);
        fillThePack();
    }
    private void fillThePack(){
        int NUMBER_OF_ARRAYS = 100;
        for (int array = 0; array< NUMBER_OF_ARRAYS; array++){
            RandomArray randomArray = new RandomArray(arrayLength);
            arrayPack.add(randomArray);
        }
    }
    public int getPackArraysLength(){
        return arrayLength;
    }

    public List<RandomArray> getArrayPack() {
        return arrayPack;
    }
}
