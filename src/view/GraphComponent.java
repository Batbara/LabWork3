package view;

import model.PointCoordinates;
import model.PointsList;

import java.awt.*;
import javax.swing.*;

public class GraphComponent extends JPanel{

    private static final int X_BORDER_GAP = 45;
    private static final int Y_BORDER_GAP = 30;
    private int scalingPrecentage;

    private Graph graph;

    public GraphComponent(){
        super();
        scalingPrecentage = 100;
        graph = new Graph(getPreferredSize(),X_BORDER_GAP,Y_BORDER_GAP, scalingPrecentage);
        this.setBackground(Color.white);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setBackground(Color.white);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graph.paint(g2);
    }


    public void setPointsList(PointsList functionData) {
        graph.setPointsList(functionData);
    }

    public void setScalingPercentage(int scalingPercentage) {
        this.scalingPrecentage = scalingPercentage;
        graph.setScalingPercentage(scalingPercentage);
    }

    @Override
    public Dimension getPreferredSize() {
        double scalingValue = (double)scalingPrecentage/100;
        return new Dimension((int) (450 *scalingValue)
                , (int) (450*scalingValue));
    }


}