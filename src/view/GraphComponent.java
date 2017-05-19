package view;

import model.PointCoordinates;
import model.PointsList;

import java.awt.*;
import javax.swing.*;

public class GraphComponent extends JPanel{

    private static final int X_BORDER_GAP = 45;
    private static final int Y_BORDER_GAP = 30;

    private Graph graph;

    public GraphComponent(){
        super();
        graph = new Graph(getPreferredSize(),X_BORDER_GAP,Y_BORDER_GAP);
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

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(450 , 450);
    }


}