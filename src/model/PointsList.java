package model;

import java.util.*;

public class PointsList {
    private List<Point> pointsList;
    private double xScale;
    private double yScale;

    public PointsList() {
        pointsList = new ArrayList<>();
        xScale = 0d;
        yScale = 0d;
    }

    public void setPointsList(PointsList newList) {
        this.pointsList = newList.pointsList;
    }

    public void addPoint(Point pointToAdd) {

        pointsList.add(pointToAdd);
    }

    public void setScaling(double xScale, double yScale) {
        this.xScale = xScale;
        this.yScale = yScale;
    }

    public List<Point> getPointsList() {
        return pointsList;
    }

    public List<PointCoordinates> getListOfCoordinates() {
        List<PointCoordinates> listOfCoordinates = new ArrayList<>();

        for (Point point : pointsList){
            Integer xCoordinate = (int) Math.ceil(point.getNumberOfElements() * xScale);
            Integer yCoordinate = (int) Math.ceil(point.getTime() * yScale);
            PointCoordinates pointCoordinates = new PointCoordinates(xCoordinate, yCoordinate);
            listOfCoordinates.add(pointCoordinates);
        }
        listOfCoordinates.sort((o1, o2) -> o1.getX() < o2.getX() ? -1 : o1.getX().equals(o2.getX()) ? 0 : 1);
        return listOfCoordinates;
    }
}
