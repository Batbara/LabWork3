package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Batbara on 02.05.2017.
 */
public class SortingAlgorithm implements Callable {
    private RandomArray arrayToSort;
    private Integer arrayLength;
    public SortingAlgorithm(RandomArray arrayToSort) {
        this.arrayToSort = arrayToSort;
        arrayLength = arrayToSort.size();
    }
    public Long call(){
        synchronized (arrayToSort) {
            long startingTime = System.currentTimeMillis();
            Integer[] array = Arrays.copyOf(arrayToSort.getRandomArray(), arrayLength, Integer[].class);
            sortMerge(array);
            long endTime = System.currentTimeMillis();
            return endTime - startingTime;
        }
    }
    private Integer[] sortMerge(Integer[] array) {
        int length = array.length;
        if (length < 2)
            return array;
        int middle = length / 2;
        return merge(sortMerge(Arrays.copyOfRange(array, 0, middle)),
                sortMerge(Arrays.copyOfRange(array, middle, length)));
    }
    private Integer[] merge(Integer[] firstArray, Integer[] secondArray) {
        int firstLength = firstArray.length, secondLength = secondArray.length;
        int elementFirstArr = 0, elementSecondArr = 0, length = firstLength + secondLength;
        Integer[] result = new Integer[length];
        for (int i = 0; i < length; i++) {
            if (elementSecondArr < secondLength && elementFirstArr < firstLength) {
                if (firstArray[elementFirstArr] > secondArray[elementSecondArr])
                    result[i] = secondArray[elementSecondArr++];
                else result[i] = firstArray[elementFirstArr++];
            } else if (elementSecondArr < secondLength) {
                result[i] = secondArray[elementSecondArr++];
            } else {
                result[i] = firstArray[elementFirstArr++];
            }
        }
        return result;
    }


    public Integer getArrayLength() {
        return arrayLength;
    }
}
