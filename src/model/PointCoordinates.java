package model;

public class PointCoordinates {
    private int xCoordinate;
    private int yCoordinate;
    public PointCoordinates(){
        xCoordinate=0;
        yCoordinate =0;
    }
    public PointCoordinates(int xCoordinate, int yCoordinate){
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }
    public Integer getY(){
        return yCoordinate;
    }
    public Integer getX(){
        return xCoordinate;
    }
}
