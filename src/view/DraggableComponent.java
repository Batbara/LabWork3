package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Batbara on 09.05.2017.
 */
public class DraggableComponent extends JComponent {
    private boolean draggable = true;

    private Point anchorPoint;

   // protected Cursor draggingCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);

    private boolean overbearing = false;

    public DraggableComponent() {
        addDragListeners();
       // setOpaque(true);
        setBackground(new Color(240,240,240));
    }
    private void addDragListeners() {
        final DraggableComponent handle = this;
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {

                    anchorPoint = e.getPoint();

                //setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    int anchorX = anchorPoint.x;
                    int anchorY = anchorPoint.y;

                    Point parentOnScreen = getParent().getLocationOnScreen();
                    Point mouseOnScreen = e.getLocationOnScreen();
                    Point position = new Point(mouseOnScreen.x - parentOnScreen.x -
                            anchorX, mouseOnScreen.y - parentOnScreen.y - anchorY);
                    setLocation(position);

                    if (overbearing) {
                        getParent().setComponentZOrder(handle, 0);
                        repaint();
                    }
                }
            }
        });
    }
}
