package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Created by Batbara on 09.05.2017.
 */
public class TableRow {
    private Vector<String> row;
    private Integer numberOfElements;
    private Long elapsedTime;
    public TableRow(Integer numberOfElements, Long elapsedTime){
        row = new Vector<>(2);
        this.numberOfElements = numberOfElements;
        this.elapsedTime = elapsedTime;
        makeRow();
    }
    private void makeRow(){
        row.add(Integer.toString(numberOfElements));
        row.add(Long.toString(elapsedTime));
    }

    public Vector<String> getRow() {
        return row;
    }
}
