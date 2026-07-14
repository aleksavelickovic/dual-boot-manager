package io.github.aleksavelickovic;


import io.github.aleksavelickovic.ui.MainWindow;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {

        List options = getOptionsList();

        SwingUtilities.invokeLater(() -> {
            MainWindow frame = new MainWindow(options);
            frame.setVisible(true);
        });
    }

    private static List getOptionsList() throws IOException {
        List options = new ArrayList<String>();

        ProcessBuilder pb = new ProcessBuilder("bash", "-c",
                "pkexec awk -F\\' '/menuentry / {print $2}' /boot/grub2/grub.cfg");

        Process process = pb.start();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                options.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return options;
    }
}
