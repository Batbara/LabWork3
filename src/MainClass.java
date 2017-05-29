import controller.*;
import view.*;

import java.io.File;
import java.io.IOException;

public class MainClass {
    public MainClass(){
        InfoLogger logger = new InfoLogger();
        DataController dataController = new DataController(logger);
        MainFrame mainFrame = new MainFrame(dataController);

    }
    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(MainClass::new);
    }
}
