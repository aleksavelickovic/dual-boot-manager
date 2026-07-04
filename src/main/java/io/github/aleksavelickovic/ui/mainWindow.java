package io.github.aleksavelickovic.ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class mainWindow extends JFrame {
    private JPanel mainPanel;
    private JComboBox comboBox;

    public mainWindow() {
        setBounds(100, 100, 417, 261);
        setLocationRelativeTo(null);
        List<String> options = new ArrayList<String>();
        options.add("String 1");
        options.add("String 2");
        String string = "stirng";
        comboBox.setModel(new DefaultComboBoxModel<Object>(options.toArray()));
        getContentPane().add(comboBox);
    }

}
