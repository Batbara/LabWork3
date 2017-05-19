package model;

import java.util.*;

public class PointsList {
    private List<PointCoordinates> listOfCoordinates;
    private Map<Integer, Long> pointsList;
    private double xScale;
    private double yScale;
    public PointsList(){
        pointsList = new HashMap<>();
        xScale=0d;
        yScale=0d;
    }
    public void setPointsList(PointsList newList){
        this.pointsList = newList.pointsList;
    }
    public void addPoint(Integer numberOfArrays, Long elapsedTime){
        pointsList.put(numberOfArrays, elapsedTime);
    }
    public void setScaling(double xScale, double yScale){
        this.xScale = xScale;
        this.yScale = yScale;
    }
    public Set<Integer> getXSet(){
        return pointsList.keySet();
    }
    public Long getTime(Integer xValue){
        return pointsList.get(xValue);
    }
    public Map<Integer, Long> getPointsList() {
        return pointsList;
    }
    public boolean isEmpty(){
        if(pointsList.isEmpty())
            return true;
        return false;
    }

    public List<PointCoordinates> getListOfCoordinates(){
         listOfCoordinates = new ArrayList<>();

        for(Integer xValue : getXSet()){

            Integer xCoordinate = (int)Math.ceil(xValue*xScale);
            Integer yCoordinate = (int) Math.ceil(pointsList.get(xValue)*yScale);
            PointCoordinates pointCoordinates = new PointCoordinates(xCoordinate, yCoordinate);
            listOfCoordinates.add(pointCoordinates);
        }
        Collections.sort(listOfCoordinates, (o1, o2) -> o1.getX() < o2.getX() ? -1 : o1.getX().equals(o2.getX()) ? 0 : 1);
        return listOfCoordinates;
    }
}
