package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.*;
import javax.swing.*;

//@SuppressWarnings("serial")
public class GraphComponent extends JComponent{
    private static final int BORDER_GAP = 40;
    private static final int GRAPH_POINT_WIDTH = 12;
    private static final int Y_HATCH_CNT = 10;
    private Map<Integer, Long> functionData;

    public GraphComponent(){
        super();
        this.setBackground(Color.white);
        this.setPreferredSize(new Dimension(650,250));
        this.setMaximumSize(this.getPreferredSize());
    }
    public GraphComponent(Map<Integer, Long> functionData) {
        this.functionData = functionData;
    }
    private Map<String, Double> setUpAxisScale(){
        Map<String, Double> scaling = new HashMap<>();
        try {
            Set<Integer> allNumberOfElements = functionData.keySet();
        }catch (NullPointerException e){
            scaling.put("x",(double)0);
            scaling.put("y", (double)0);
            return scaling;
        }
        Set<Integer> allNumberOfElements = functionData.keySet();
        Long maxTime = 0L;
        Integer maxNumberOfElements = 0;

        for (Integer elements : allNumberOfElements){
            if(functionData.get(elements)>maxTime)
                maxTime=functionData.get(elements);
            if(elements>maxNumberOfElements)
                maxNumberOfElements=elements;
        }
        double y_scaling = (double)(getHeight()-2*BORDER_GAP)/maxTime;
        double x_scaling = (double)getWidth()/maxNumberOfElements;


        scaling.put("x",x_scaling);
        scaling.put("y", y_scaling);
        return scaling;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


       drawAxis(g2);

    }
    private void drawAxis(Graphics2D g2){
        // create x and y axes
        g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, BORDER_GAP, BORDER_GAP);

        g2.drawLine(BORDER_GAP, BORDER_GAP, BORDER_GAP+5, BORDER_GAP+10);
        g2.drawLine(BORDER_GAP, BORDER_GAP, BORDER_GAP-5, BORDER_GAP+10);
        g2.drawString("t, мкс", BORDER_GAP-5, BORDER_GAP-5);

        g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, getWidth() - BORDER_GAP, getHeight() - BORDER_GAP);

        // create hatch marks for y axis.
        //Map<String, Double> scaling = setUpAxisScale();
        double xScale = setUpAxisScale().get("x");
        double yScale = setUpAxisScale().get("y");

        for (int i = 0; i < Y_HATCH_CNT-1; i++) {

            int x0 = BORDER_GAP-5;
            int x1 = GRAPH_POINT_WIDTH + BORDER_GAP-5;
            int y0 = getHeight() - (((i + 1) * (getHeight() - BORDER_GAP * 2)) / Y_HATCH_CNT + BORDER_GAP);
            int y1 = y0;
            if(!(yScale==0)) {
                int label = (int) Math.round((double) yScale * i);

                g2.drawString(Integer.toString(label), x0 - 15, y0);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        // and for x axis
        /*for (int i = 0; i < maxNumberOfElements/increasingStep; i++) {
            //int x0 = (i + 1) * (getWidth() - BORDER_GAP * 2) / (scores.size() - 1) + BORDER_GAP;
            int x0= (i+1)*increasingStep+BORDER_GAP;
            int x1 = x0;
            int y0 = getHeight() - BORDER_GAP;
            int y1 = y0 - GRAPH_POINT_WIDTH;
            g2.drawLine(x0, y0, x1, y1);
        }*/
    }

    public void setFunctionData(Map<Integer, Long> functionData) {
        this.functionData = functionData;
    }



}