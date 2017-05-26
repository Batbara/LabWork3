package model;

public class Point {
    private Long time;
    private Integer numberOfElements;

    public Point(Long time, Integer numberOfElements) {
        this.time = time;
        this.numberOfElements = numberOfElements;
    }

    public Long getTime() {
        return time;
    }

    public Integer getNumberOfElements() {
        return numberOfElements;
    }
}
