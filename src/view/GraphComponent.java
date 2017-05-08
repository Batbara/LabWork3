package view;

import javax.swing.*;
import java.awt.*;

public class GraphComponent extends JComponent{
    private JPanel graphPanel;

    public GraphComponent(){
        graphPanel = new JPanel(new FlowLayout());
        graphPanel.setPreferredSize(new Dimension(500,400));
    }
}
