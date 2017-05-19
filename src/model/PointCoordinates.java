package model;

public class PointCoordinates {
    private int xCoordinate;
    private int yCoordinate;
    private String paintingStatus;

    public PointCoordinates(){
        xCoordinate=0;
        yCoordinate =0;
        paintingStatus = "none";
    }
    public PointCoordinates(int xCoordinate, int yCoordinate){
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        paintingStatus = "none";
    }

    public String getPaintingStatus() {
        return paintingStatus;
    }

    public void setPaintingStatus(String paintingStatus) {
        this.paintingStatus = paintingStatus;
    }

    public Integer getY(){
        return yCoordinate;
    }
    public Integer getX(){
        return xCoordinate;
    }
}
