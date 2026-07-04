package io.github.aleksavelickovic;


import io.github.aleksavelickovic.ui.MainWindow;

import javax.swing.*;

public class Main {
    static void main(String[] args) {
        System.out.println("Hello World!");
        SwingUtilities.invokeLater(() -> {
            MainWindow frame = new MainWindow();
            frame.setVisible(true);
        });
    }
}
