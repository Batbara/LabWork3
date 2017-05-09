package view;

import controller.DataController;
import model.TableRow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainFrame {
    private DataController dataController;
    private JTable dataTable;
    private JFrame mainFrame;
    private Map<String,JTextField> dataFields;
    private JButton buttonToSendData;

    public MainFrame(DataController dataController){
        this.dataController = dataController;
        dataFields = new LinkedHashMap<>();
        buttonToSendData = new JButton("Посчитать");
        dataFields.put("Max количество элементов:", new JTextField(10));
        dataFields.put("Количество массивов: ", new JTextField(10));
        initFrame();
        initTable();

        JPanel tableAndDataPanel = createTableAndDataPanel();
        mainFrame.add(tableAndDataPanel);
        addButtonListener();

    }
    private void initFrame(){
        mainFrame = new JFrame("Лабораторная работа №3");
        mainFrame.setLayout(new FlowLayout());
        mainFrame.setPreferredSize(new Dimension(950, 800));
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setSize(new Dimension(1050, 476));
        mainFrame.setMaximumSize(new Dimension(850, 700));

        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
    }
    private void initTable(){
        String []columnNames= {"n", "t"};
        dataTable = new JTable(new DefaultTableModel(columnNames,0));
        dataTable.setSize(new Dimension(50,500));
    }
    private JPanel createFieldsPanel(){
        JPanel holdingPanel = new JPanel(new GridLayout(2,2));
        Set<String> labels = dataFields.keySet();
        for (String label : labels ){
            holdingPanel.add(new JLabel(label));
            holdingPanel.add(dataFields.get(label));
        }
        return holdingPanel;
    }
    private JPanel createTableAndDataPanel(){
        JPanel tableAndDataPanel = new JPanel();
        JPanel fieldsPanel = createFieldsPanel();

        tableAndDataPanel.setLayout(new BoxLayout(tableAndDataPanel,BoxLayout.Y_AXIS));
        tableAndDataPanel.setPreferredSize(new Dimension(350,350));

        JPanel buttonHoldingTable = new JPanel(new FlowLayout());
        buttonHoldingTable.add(buttonToSendData);

        tableAndDataPanel.add(fieldsPanel);
        tableAndDataPanel.add(buttonHoldingTable);
        tableAndDataPanel.add(new JScrollPane(dataTable));

        return tableAndDataPanel;
    }
    private void addButtonListener(){
        buttonToSendData.addActionListener(e -> {
            Set<String> fieldKeys = dataFields.keySet();
            for (String key : fieldKeys){
                dataController.setByKey(key, dataFields.get(key).getText());
            }
            dataController.createArrays();
            try {
                dataController.sorting();
            } catch (ExecutionException e1) {
                e1.printStackTrace();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            fillTable(dataController.getListOfRows());
        });
    }
    private void fillTable(List<TableRow> listOfRows){

        DefaultTableModel tableModel = (DefaultTableModel)dataTable.getModel();
        for (TableRow row : listOfRows){
            tableModel.addRow(row.getRow());
        }

        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(tableModel);
        Comparator<String> elementsNumberComparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if(Integer.parseInt(o1)>Integer.parseInt(o2))
                    return 1;
                if(Integer.parseInt(o1)<Integer.parseInt(o2))
                    return -1;
                return 0;
            }
        };
        rowSorter.setComparator(0, elementsNumberComparator);
        rowSorter.setComparator(1, elementsNumberComparator);

        dataTable.setRowSorter(rowSorter);
        dataTable.getRowSorter().toggleSortOrder(0);
    }
}
