package io.github.aleksavelickovic.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class MainWindow extends JFrame {
    private JPanel mainPanel;
    private JComboBox comboBox;
    private JButton rebootButton;

    public MainWindow(List<String> options) {
        options.addFirst("Fedora Linux");

        setContentPane(mainPanel);
        setLocationRelativeTo(null);
        setSize(640, 360);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        comboBox.setModel(new DefaultComboBoxModel<Object>(options.toArray()));
        comboBox.setSelectedIndex(0);

        rebootButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
//                    System.out.println((String) comboBox.getSelectedItem());
                    rebootButtonOnClick((String) comboBox.getSelectedItem());
                } catch (Exception e) { // TODO change to IOException later
                    throw new RuntimeException(e);
                }
            }
        });

        revalidate();
        repaint();
    }

    public static void rebootButtonOnClick(String selectedValue) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("bash", "-c",
                "pkexec grub2-reboot \"" + selectedValue + "\"");
        Process process1 = pb.start();

        while (process1.isAlive()) {
            Thread.sleep(500);
        }

        ProcessBuilder pb2 = new ProcessBuilder("bash", "-c", "reboot");
        Process process2 = pb2.start();

    }
}
