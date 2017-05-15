package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Batbara on 12.05.2017.
 */
public class ArrayPack {
    private static int NUMBER_OF_ARRAYS = 100;
    private int arrayLength;
    List<RandomArray> arrayPack;
    public ArrayPack(int arrayLength){
        this.arrayLength = arrayLength;
        arrayPack = new ArrayList<>(arrayLength);
        fillThePack();
    }
    private void fillThePack(){
        for (int array=0; array<NUMBER_OF_ARRAYS; array++){
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
