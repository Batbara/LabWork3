package view;

import model.PointCoordinates;
import model.PointsList;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.util.List;

public class Graph {

    private static final int GRAPH_POINT_WIDTH = 8;
    private static final int Y_MARKS_NUM = 10;
    private static final int X_MARKS_NUM = 10;

    private Dimension componentSize;
    private int X_BORDER_GAP;
    private int Y_BORDER_GAP;
    private PointsList graphPoints;
    private int xAxisLength;
    private int yAxisLength;
    private double scalingValue;

    public Graph(Dimension componentSize, int x_BORDER_GAP, int y_BORDER_GAP, int scalingPercentage){
        
        graphPoints = new PointsList();
        this.componentSize = componentSize;
        this.scalingValue = (double)scalingPercentage/100;
        this.X_BORDER_GAP = (int)(x_BORDER_GAP*scalingValue);
        this.Y_BORDER_GAP = (int)(y_BORDER_GAP*scalingValue);
        xAxisLength = (int)componentSize.getWidth()-(2*X_BORDER_GAP+15);
        yAxisLength = (int)componentSize.getHeight()-(2*Y_BORDER_GAP+15);
    }

    public synchronized void  setPointsList(PointsList graphPoints) {
        this.graphPoints = graphPoints;
    }

    public void setScalingPercentage(int scalingPercentage) {
        this.scalingValue = (double)scalingPercentage/100;
    }

    public void paint(Graphics2D g2){

        paintAxis(g2);

        paintGraphLine(g2);
    }
    public void paintAxis(Graphics2D g2){

        AffineTransform original = g2.getTransform();
        AffineTransform scaled = new AffineTransform(original);
        scaled.scale(scalingValue, scalingValue);
        g2.setTransform(scaled);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            double xScale = setUpAxisScale().get("x");
            double yScale = setUpAxisScale().get("y");

            int yPointOfOrigin = (int)componentSize.getHeight() - Y_BORDER_GAP;
            int xPointOfOrigin = (int)componentSize.getWidth() - X_BORDER_GAP;

            g2.drawString("0", X_BORDER_GAP,yPointOfOrigin+10);

            g2.drawLine(X_BORDER_GAP, yPointOfOrigin, X_BORDER_GAP, Y_BORDER_GAP);
            g2.drawLine(X_BORDER_GAP, Y_BORDER_GAP, X_BORDER_GAP+5, Y_BORDER_GAP+10);
            g2.drawLine(X_BORDER_GAP, Y_BORDER_GAP, X_BORDER_GAP-5, Y_BORDER_GAP+10);
            g2.drawString("t, мкс", X_BORDER_GAP-5, Y_BORDER_GAP-5);

            g2.drawLine(X_BORDER_GAP, yPointOfOrigin, xPointOfOrigin, yPointOfOrigin);
            g2.drawLine(xPointOfOrigin, yPointOfOrigin, xPointOfOrigin-10, yPointOfOrigin-5);
            g2.drawLine(xPointOfOrigin, yPointOfOrigin, xPointOfOrigin-10, yPointOfOrigin+5);
            g2.drawString("n", xPointOfOrigin+5, yPointOfOrigin);

            for (int i = 0; i < Y_MARKS_NUM; i++) {

                int x0 = X_BORDER_GAP-5;
                int x1 = GRAPH_POINT_WIDTH + X_BORDER_GAP-5;
                int y0 = (int)componentSize.getHeight()-( ((i + 1) * (yAxisLength+15)) / Y_MARKS_NUM  +Y_BORDER_GAP-15);
                int y1 = y0;
                if(!(yScale==0)) {

                    int label = (int) Math.round(yScale * (yAxisLength+15 -y0+Y_BORDER_GAP));

                    g2.drawString(Integer.toString(label), x1 - (X_BORDER_GAP-6), y1+7);
                }
                g2.drawLine(x0, y0, x1, y1);
            }

            for (int i = 0; i < X_MARKS_NUM; i++) {
                int x0 = (i + 1) * (xAxisLength) / X_MARKS_NUM + X_BORDER_GAP;
                int x1 = x0;
                int y0 =yPointOfOrigin+5;
                int y1 = y0 - GRAPH_POINT_WIDTH;
                if(!(xScale==0)) {

                    int label = (int) Math.ceil(xScale * (x0-X_BORDER_GAP));
                    g2.drawString(Integer.toString(label), x1-7, y1+30);
                }
                g2.drawLine(x0, y0, x1, y1);
            }
        }

    private synchronized void paintGraphLine(Graphics2D g2) {

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke( new BasicStroke(3f));
        double xScale = setUpAxisScale().get("xCoordinates");
        double yScale = setUpAxisScale().get("yCoordinates");

        graphPoints.setScaling(xScale, yScale);
        List<PointCoordinates> listOfCoordinates = graphPoints.getListOfCoordinates();

        for (int point=0; point<listOfCoordinates.size()-1; point++){
            PointCoordinates firstPointCoordinates = listOfCoordinates.get(point);
            PointCoordinates secondPointCoordinates = listOfCoordinates.get(point+1);

            int x = X_BORDER_GAP+firstPointCoordinates.getX();
            int y = (yAxisLength+15)+ Y_BORDER_GAP - firstPointCoordinates.getY();

            int x2 = X_BORDER_GAP+secondPointCoordinates.getX();
            int y2 = (yAxisLength+15)+Y_BORDER_GAP - secondPointCoordinates.getY();
            g2.setColor(Color.yellow);
            g2.drawLine(x, y, x2, y2);

            g2.setColor(Color.orange);
            int ovalW = GRAPH_POINT_WIDTH;
            int ovalH = GRAPH_POINT_WIDTH;
            g2.fillOval(x  - GRAPH_POINT_WIDTH / 2, y  - GRAPH_POINT_WIDTH / 2, ovalW, ovalH);
            if(point==listOfCoordinates.size()-2)
                g2.fillOval(x2  - GRAPH_POINT_WIDTH / 2, y2  - GRAPH_POINT_WIDTH / 2, ovalW, ovalH);

        }


    }

    private  Map<String, Double> setUpAxisScale(){
        Map<String, Double> scaling = new HashMap<>();
        Set<Integer> allNumberOfElements = graphPoints.getXSet();
        Long maxTime = 0L;
        Integer maxNumberOfElements = 0;

        for (Integer elements : allNumberOfElements){
            if(graphPoints.getTime(elements)>maxTime)
                maxTime= graphPoints.getTime(elements);
            if(elements>maxNumberOfElements)
                maxNumberOfElements=elements;
        }

        double yScale = (double)maxTime/ yAxisLength;
        double xScale = (double)maxNumberOfElements/ xAxisLength;

        double xCoordinateScale = (double) xAxisLength /maxNumberOfElements;
        double yCoordinatesScale = (double) yAxisLength /maxTime;

        scaling.put("x",xScale);
        scaling.put("y", yScale);
        scaling.put("xCoordinates", xCoordinateScale);
        scaling.put("yCoordinates", yCoordinatesScale);

        return scaling;
    }
}
