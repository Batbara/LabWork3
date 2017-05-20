package model;

import java.util.Vector;

public class TableRow {
    private Vector<String> row;
    private Integer numberOfElements;
    private Long elapsedTime;

    public TableRow(Integer numberOfElements, Long elapsedTime) {
        row = new Vector<>(2);
        this.numberOfElements = numberOfElements;
        this.elapsedTime = elapsedTime;
        makeRow();
    }

    private void makeRow() {
        row.add(Integer.toString(numberOfElements));
        row.add(Long.toString(elapsedTime));
    }

    public Vector<String> getRow() {
        return row;
    }
}
