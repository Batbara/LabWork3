package view;

import model.PointCoordinates;
import model.PointsList;

import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

//@SuppressWarnings("serial")
public class GraphComponent extends JComponent{

    private static final int BORDER_GAP = 30;
    private static final int X_BORDER_GAP = 45;
    private static final int Y_BORDER_GAP = 30;
    private static final int Y_AXIS_TOP = BORDER_GAP-15;
    private static final Stroke GRAPH_STROKE = new BasicStroke(3f);
    private static final int GRAPH_POINT_WIDTH = 8;
    private static final int Y_MARKS_NUM = 10;
    private static final int X_MARKS_NUM = 10;

    private PointsList graphPoints;

    public GraphComponent(){
        super();
        graphPoints = new PointsList();
        this.setBackground(Color.white);
        this.setPreferredSize(new Dimension(650,250));
        this.setMaximumSize(this.getPreferredSize());
    }

    private  Map<String, Double> setUpAxisScale(){
        Map<String, Double> scaling = new HashMap<>();
        try {
            Set<Integer> allNumberOfElements = graphPoints.getXSet();
        }catch (NullPointerException e){
            scaling.put("x",(double)0);
            scaling.put("y", (double)0);
            return scaling;
        }
        Set<Integer> allNumberOfElements = graphPoints.getXSet();
        Long maxTime = 0L;
        Integer maxNumberOfElements = 0;

        for (Integer elements : allNumberOfElements){
            if(graphPoints.getTime(elements)>maxTime)
                maxTime= graphPoints.getTime(elements);
            if(elements>maxNumberOfElements)
                maxNumberOfElements=elements;
        }

         double yScale = (double)maxTime/(getHeight()-(2*Y_BORDER_GAP+15));
         double xScale = (double)maxNumberOfElements/(getWidth()-(2*X_BORDER_GAP+15));

         double xCoordinateScale = (double)(getWidth()-(2*X_BORDER_GAP+15))/maxNumberOfElements;
         double yCoordinatesScale = (double)(getHeight()-(2*Y_BORDER_GAP+15))/maxTime;

        scaling.put("x",xScale);
        scaling.put("y", yScale);
        scaling.put("xCoordinates", xCoordinateScale);
        scaling.put("yCoordinates", yCoordinatesScale);

        return scaling;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setBackground(Color.white);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawAxis(g2);
        try {
            if (!(graphPoints.isEmpty())) {

                setLabels(g2);
            }
        }catch (NullPointerException e){
            System.out.println("Exception caught!");
        }

    }
    private void drawAxis(Graphics2D g2){
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        System.out.println("start draw axis");
        double xScale = setUpAxisScale().get("x");
        double yScale = setUpAxisScale().get("y");

        int yAxisLength = getHeight() - Y_BORDER_GAP;
        int xAxisLength = getWidth() - X_BORDER_GAP;

        g2.drawString("0", X_BORDER_GAP-5,yAxisLength+10);

        g2.drawLine(X_BORDER_GAP, yAxisLength, X_BORDER_GAP, Y_BORDER_GAP);
        g2.drawLine(X_BORDER_GAP, Y_BORDER_GAP, X_BORDER_GAP+5, Y_BORDER_GAP+10);
        g2.drawLine(X_BORDER_GAP, Y_BORDER_GAP, X_BORDER_GAP-5, Y_BORDER_GAP+10);
        g2.drawString("t, мкс", X_BORDER_GAP-5, Y_BORDER_GAP-5);

        g2.drawLine(X_BORDER_GAP, yAxisLength, xAxisLength, yAxisLength);
        g2.drawLine(xAxisLength, yAxisLength, xAxisLength-10, yAxisLength-5);
        g2.drawLine(xAxisLength, yAxisLength, xAxisLength-10, yAxisLength+5);
        g2.drawString("n", xAxisLength+5, yAxisLength);

        for (int i = 0; i < Y_MARKS_NUM; i++) {

            int x0 = X_BORDER_GAP-5;
            int x1 = GRAPH_POINT_WIDTH + X_BORDER_GAP-5;
            int y0 = getHeight() - (((i + 1) * (getHeight() - BORDER_GAP * 2)) / Y_MARKS_NUM + Y_BORDER_GAP-15);
            int y1 = y0;
            g2.drawLine(x0, y0, x1, y1);
        }


        for (int i = 0; i < X_MARKS_NUM; i++) {
            int x0 = (i + 1) * (getWidth() - (X_BORDER_GAP * 2+15)) / X_MARKS_NUM + X_BORDER_GAP;
            int x1 = x0;
            int y0 = getHeight() - Y_BORDER_GAP+5;
            int y1 = y0 - GRAPH_POINT_WIDTH;
            g2.drawLine(x0, y0, x1, y1);
        }
    }
    private void setLabels(Graphics2D g2){
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        System.out.println("start draw axis");
        double xScale = setUpAxisScale().get("x");
        double yScale = setUpAxisScale().get("y");
        for (int i = 0; i < Y_MARKS_NUM; i++) {
            int x0 = X_BORDER_GAP-5;
            int x1 = GRAPH_POINT_WIDTH + X_BORDER_GAP-5;
            int y0 = getHeight() - (((i + 1) * (getHeight() - BORDER_GAP * 2)) / Y_MARKS_NUM + Y_BORDER_GAP-15);
            int y1 = y0;
            if(!(yScale==0)) {

                int label = (int) Math.round((double) yScale * ((getHeight() - Y_BORDER_GAP * 2)-y0+Y_BORDER_GAP));

                g2.drawString(Integer.toString(label), x1 - 39, y1+7);
            }
            g2.drawLine(x0, y0, x1, y1);
        }
        for (int i = 0; i < X_MARKS_NUM; i++) {
            int x0 = (i + 1) * (getWidth() - (X_BORDER_GAP * 2+15)) / X_MARKS_NUM + X_BORDER_GAP;
            int x1 = x0;
            int y0 = getHeight() - Y_BORDER_GAP+5;
            int y1 = y0 - GRAPH_POINT_WIDTH;
            if(!(xScale==0)) {

                int label = (int) Math.ceil((double) xScale * (x0-X_BORDER_GAP));
                g2.drawString(Integer.toString(label), x1-7, y1+30);
            }
        }
    }
    public void drawGraph(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.red);
        g2.setStroke( new BasicStroke(3f));
        double xScale = setUpAxisScale().get("xCoordinates");
        double yScale = setUpAxisScale().get("yCoordinates");


        g2.setColor(Color.blue);

        graphPoints.setScaling(xScale, yScale);
        graphPoints.makeCoordinates();

        List<PointCoordinates> listOfCoordinates = graphPoints.getListOfCoordinates();
        PointCoordinates firstPointCoordinates = new PointCoordinates();
        PointCoordinates secondPointCoordinates = new PointCoordinates();

        for (int point=0; point<listOfCoordinates.size()-1; point++){
            System.out.println("create graph between "+point+" and "+(point+1));
            firstPointCoordinates = listOfCoordinates.get(point);
            secondPointCoordinates = listOfCoordinates.get(point+1);

            int x = X_BORDER_GAP+firstPointCoordinates.getX();
            int y = (getHeight() - Y_BORDER_GAP * 2)+ Y_BORDER_GAP - firstPointCoordinates.getY();

            int x2 = X_BORDER_GAP+secondPointCoordinates.getX();
            int y2 = (getHeight() - Y_BORDER_GAP * 2)+Y_BORDER_GAP - secondPointCoordinates.getY();

            g2.setColor(Color.red);
            int ovalW = GRAPH_POINT_WIDTH;
            int ovalH = GRAPH_POINT_WIDTH;
            g2.fillOval(x  - GRAPH_POINT_WIDTH / 2, y  - GRAPH_POINT_WIDTH / 2, ovalW, ovalH);
            if(point==listOfCoordinates.size()-2)
                g2.fillOval(x2  - GRAPH_POINT_WIDTH / 2, y2  - GRAPH_POINT_WIDTH / 2, ovalW, ovalH);

            g2.setColor(Color.blue);
            g2.drawLine(x, y, x2, y2);
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    public void setPointsList(PointsList functionData) {
        this.graphPoints = functionData;
    }



}