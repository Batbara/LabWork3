import controller.*;
import view.*;
public class MainClass {
    public MainClass(){
        DataController dataController = new DataController();
        MainFrame mainFrame = new MainFrame(dataController);
    }
    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(MainClass::new);
    }
}
