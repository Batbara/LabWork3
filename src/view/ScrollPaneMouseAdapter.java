package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ScrollPaneMouseAdapter implements MouseListener, MouseMotionListener {
    private Point startPoint;
    private Point endPoint;
    private JScrollPane scrollPane;
    private GraphComponent graphComponent;
    public ScrollPaneMouseAdapter(JScrollPane scrollPane, GraphComponent graphComponent){
        this.scrollPane = scrollPane;
        this.graphComponent = graphComponent;
        startPoint = new Point();
        endPoint = new Point();
    }

    public void mouseDragged(MouseEvent e) {
        System.out.println("position is "+e.getX()+", "+e.getY());
        JViewport viewPort = scrollPane.getViewport();
        Point viewPortPosition = viewPort.getViewPosition();
        viewPortPosition.translate((int)startPoint.getX()-e.getX(),(int)startPoint.getY()-e.getY());
        graphComponent.scrollRectToVisible(new Rectangle(viewPortPosition, viewPort.getSize()));
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        startPoint = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        endPoint = e.getPoint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
