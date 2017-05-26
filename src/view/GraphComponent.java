package view;

import model.PointsList;

import javax.swing.*;
import java.awt.*;

public class GraphComponent extends JComponent {

    private static final int X_BORDER_GAP = 45;
    private static final int Y_BORDER_GAP = 30;
    private int scalingPercentage;

    private Graph graph;

    public GraphComponent() {
        super();
        scalingPercentage = 100;
        graph = new Graph(getPreferredSize(), X_BORDER_GAP, Y_BORDER_GAP, scalingPercentage);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graph.paint(g2);
    }

    public void setPointsList(PointsList functionData) {
        graph.setPointsList(functionData);
    }

    public void setScalingPercentage(int scalingPercentage) {
        this.scalingPercentage = scalingPercentage;
        graph.setScalingPercentage(scalingPercentage);
    }

    public int getScalingPercentage() {
        return scalingPercentage;
    }

    public Graph getGraph() {
        return graph;
    }

    @Override
    public Dimension getSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getPreferredSize() {
        double scalingValue = (double) scalingPercentage / 100;
        return new Dimension((int) (650 * (scalingValue * scalingValue))
                , (int) (400 * (scalingValue * scalingValue)));
    }


}