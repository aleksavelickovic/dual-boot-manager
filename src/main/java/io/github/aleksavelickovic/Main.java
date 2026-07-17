package io.github.aleksavelickovic;


import com.formdev.flatlaf.FlatDarkLaf;
import io.github.aleksavelickovic.service.ProcessService;
import io.github.aleksavelickovic.service.impl.ProcessServiceImpl;
import io.github.aleksavelickovic.ui.MainWindow;
import io.github.aleksavelickovic.ui.utils.TrayManager;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final ProcessService processService = ProcessServiceImpl.getInstance();

    static void main(String[] args) throws IOException {
        List options = getOptionsList();

        FlatDarkLaf.setup();
        SwingUtilities.invokeLater(() -> {
            MainWindow frame = new MainWindow(options, processService);
            new TrayManager(frame, processService);
            frame.setVisible(false);
        });
    }

    private static List getOptionsList() throws IOException {
        List options = new ArrayList<String>();

        Process process = processService.execute(ProcessService.READ_GRUB, true);

        try (BufferedReader reader = new BufferedReader( // TODO refactor into a separate service
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
