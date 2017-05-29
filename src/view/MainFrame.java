package view;

import controller.DataController;
import model.PointsList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class MainFrame {

    private DataController dataController;
    private GraphComponent graphComponent;
    private JTable dataTable;
    private JFrame mainFrame;
    private Map<String, JTextField> dataFields;
    private JSpinner scalingSpinner;
    private JButton buttonToSendData;

    public MainFrame(DataController dataController) {
        this.dataController = dataController;
        dataFields = new LinkedHashMap<>();
        buttonToSendData = new JButton("Построить график");
        dataFields.put("Max количество элементов:", new JTextField(10));
        dataFields.put("Шаг:", new JTextField(10));

        initFrame();
        initTable();
        dataController.setDataTable(dataTable);

        JPanel tableAndDataPanel = createTableAndDataPanel();
        mainFrame.add(tableAndDataPanel, BorderLayout.WEST);
        addButtonListener();

        graphComponent = new GraphComponent();
        graphComponent.setPreferredSize(new Dimension(650, 400));
        graphComponent.scrollRectToVisible(new Rectangle(0, 0, 100, 100));
        dataController.setGraphComponent(graphComponent);

        scalingSpinner = new JSpinner();
        JPanel graphAndScalingPanel = createGraphAndScalingPanel();
        mainFrame.add(graphAndScalingPanel);
    }

    private void initFrame() {
        mainFrame = new JFrame("Лабораторная работа №3");
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setPreferredSize(new Dimension(950, 800));
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setSize(new Dimension(1050, 476));
        mainFrame.setMaximumSize(new Dimension(850, 700));

        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
    }

    private void initTable() {
        String[] columnNames = {"n", "t"};
        dataTable = new JTable(new DefaultTableModel(columnNames, 0));
        dataTable.setSize(new Dimension(50, 500));
    }

    private JPanel createFieldsPanel() {
        JPanel holdingPanel = new JPanel(new GridLayout(2, 2));
        Set<String> labels = dataFields.keySet();
        for (String label : labels) {
            holdingPanel.add(new JLabel(label));
            holdingPanel.add(dataFields.get(label));
        }
        return holdingPanel;
    }

    private JPanel createTableAndDataPanel() {
        JPanel tableAndDataPanel = new JPanel();
        JPanel fieldsPanel = createFieldsPanel();

        tableAndDataPanel.setLayout(new BoxLayout(tableAndDataPanel, BoxLayout.Y_AXIS));
        tableAndDataPanel.setPreferredSize(new Dimension(350, 350));

        JPanel buttonHoldingTable = new JPanel(new FlowLayout());
        buttonHoldingTable.add(buttonToSendData);

        tableAndDataPanel.add(fieldsPanel);
        tableAndDataPanel.add(buttonHoldingTable);
        tableAndDataPanel.add(new JScrollPane(dataTable));

        return tableAndDataPanel;
    }

    private JScrollPane createGraphHolder() {

        JScrollPane graphHolder = new JScrollPane(graphComponent);
        graphHolder.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        graphHolder.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        graphHolder.setBackground(Color.white);

        graphHolder.setPreferredSize(new Dimension(700, 450));
        graphHolder.setMaximumSize(graphHolder.getPreferredSize());
        graphHolder.setSize(graphHolder.getPreferredSize());

        graphHolder.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                super.mouseWheelMoved(e);
                if ((e.getModifiers() & InputEvent.CTRL_MASK) == InputEvent.CTRL_MASK) {
                    int currentScale = graphComponent.getScalingPercentage();
                    if (e.getPreciseWheelRotation() < 0) {

                        if (currentScale > 50) {
                            graphComponent.setScalingPercentage(currentScale - 5);
                            scalingSpinner.setValue(currentScale - 5);
                            graphComponent.repaint();
                        }
                    } else {
                        if (currentScale < 200) {
                            graphComponent.setScalingPercentage(currentScale + 5);
                            scalingSpinner.setValue(currentScale + 5);
                            graphComponent.repaint();
                        }

                    }
                }

            }
        });

        ScrollPaneMouseAdapter scrollAdapter = new ScrollPaneMouseAdapter(graphHolder, graphComponent);
        graphHolder.getViewport().addMouseMotionListener(scrollAdapter);
        graphHolder.getViewport().addMouseListener(scrollAdapter);
        return graphHolder;
    }

    private JPanel createGraphAndScalingPanel() {
        JScrollPane graphHolder = createGraphHolder();
        JPanel graphAndScalingPanel = new JPanel();
        graphAndScalingPanel.setLayout(new BoxLayout(graphAndScalingPanel, BoxLayout.Y_AXIS));

        JPanel scalingPanel = new JPanel(new GridLayout(1, 2));
        SpinnerModel scalingSpinnerModel = new SpinnerNumberModel(100, 1, 150, 5);
        scalingSpinner = new JSpinner(scalingSpinnerModel);
        scalingSpinner.addChangeListener(e -> {
            JSpinner spinner = (JSpinner) e.getSource();
            graphComponent.setScalingPercentage((Integer) spinner.getValue());
            graphComponent.setSize(graphComponent.getPreferredSize());
            graphComponent.repaint();
//            System.out.println("component size is" + graphComponent.getSize());
//            System.out.println("component preferred size is" + graphComponent.getPreferredSize());
//            System.out.println("component max size is" + graphComponent.getMaximumSize());
        });

        scalingPanel.add(new JLabel("Масштаб, %"));
        scalingPanel.add(scalingSpinner);
        scalingPanel.setPreferredSize(new Dimension(150, 25));
        scalingPanel.setMaximumSize(scalingPanel.getPreferredSize());
        scalingPanel.setSize(scalingPanel.getPreferredSize());

        graphAndScalingPanel.add(graphHolder);
        graphAndScalingPanel.add(scalingPanel);

        return graphAndScalingPanel;
    }

    private void addButtonListener() {
        buttonToSendData.addActionListener(e -> {
            clearTable();
            graphComponent.setPointsList(new PointsList());
            Set<String> fieldKeys = dataFields.keySet();
            for (String key : fieldKeys) {
                dataController.setByKey(key, dataFields.get(key).getText());
            }
            dataController.createArrays();
            InfoLogger logger = dataController.getLogger();
           try {
                logger.logData(dataController.getFunctionData());
            } catch (IOException exep) {
                exep.printStackTrace();
            }
            try {
                dataController.sorting();


            } catch (ExecutionException | InterruptedException e1) {
                e1.printStackTrace();
            }
            System.out.println(InfoLogger.getConstructorCalls());
        });

    }

    private void clearTable() {
        DefaultTableModel tableModel = (DefaultTableModel) dataTable.getModel();
        tableModel.setRowCount(0);
    }
}
