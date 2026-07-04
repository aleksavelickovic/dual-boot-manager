package io.github.aleksavelickovic.ui;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MainWindow extends JFrame {
    private JPanel mainPanel;
    private JComboBox comboBox;

    public MainWindow() {
        setContentPane(mainPanel);
        setLocationRelativeTo(null);
//        setBounds(100, 100, 1280, 720);
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        List<String> options = new ArrayList<String>();
        options.add("String 1");
        options.add("String 2");
        comboBox.setModel(new DefaultComboBoxModel<Object>(options.toArray()));

        revalidate();
        repaint();
    }
}
