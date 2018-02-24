import CSV.CSVController;
import gui.MainWin;

import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
//        System.out.println("Hello.");
//        System.out.println(System.getProperty("user.dir"));
//
//        CSVController controller = new CSVController("csv.csv");
//        try {
//            controller.extractColumns("out.csv");
//        } catch (IOException e) {
//            System.out.println("Help me.");
//            e.printStackTrace();
//        }
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // DEATH - dm the default UI should stay.
        }
        MainWin theWin = new MainWin("CSV Manipulator");
        System.out.println("Hmm");
        theWin.showWindow();
    }

}
