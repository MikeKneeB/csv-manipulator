package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import CSV.CSVController;

public class MainWin extends JFrame {

    private static final String ABOUT_TEXT = "Takes an input csv file, and appends all the data from the columns specified in\n" +
            "the output file. E.g. If you have an input csv with columns labelled 'one',\n" +
            "'two' & 'three' and an output csv with columns 'one' and 'three'. All the data\n" +
            "in the columns 'one' and 'three' from the input are added to the end of the\n" +
            "output.\n\n" +
            "Caveats:\n" +
            "1) The columns in the input and output must be in the same order.\n" +
            "2) The output file can contain only one or no trailing newline.\n";

    private JPanel mainPanel;
    private JFormattedTextField inputFileField;
    private JButton outputFileSelectorButton;
    private JFormattedTextField outputFileField;
    private JLabel outputFileLabel;
    private JButton inputFileSelectorButton;
    private JLabel inputFileLabel;
    private JButton goButton;
    private JButton aboutButton;
    private JLabel separatorLabel;
    private JComboBox separatorCombo;
    private final JFileChooser fileChooser = new JFileChooser();

    public MainWin(String name) {

        super(name);

        String[] seps = new String[2];
        seps[0] = ",";
        seps[1] = ";";

        DefaultComboBoxModel model = new DefaultComboBoxModel(seps);

        separatorCombo.setModel(model);

        inputFileSelectorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fileChooser.showOpenDialog(mainPanel);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    inputFileField.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });

        outputFileSelectorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fileChooser.showOpenDialog(mainPanel);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    outputFileField.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });

        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean success;
                CSVController controller = new CSVController(inputFileField.getText(), (String) separatorCombo.getSelectedItem());
                try {
                    controller.extractColumns(outputFileField.getText());
                    success = true;
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(mainPanel, "An error occured!", "Error", JOptionPane.ERROR_MESSAGE);
                    success = false;
                }
                if (success) {
                    JOptionPane.showMessageDialog(mainPanel, "Done!", "Message", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(mainPanel, ABOUT_TEXT, "About", JOptionPane.INFORMATION_MESSAGE);
            }
        });

    }

    public void showWindow() {
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setSize(500, this.getHeight());
        this.pack();
        this.setVisible(true);
    }
}
