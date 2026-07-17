package io.github.aleksavelickovic.ui.utils;

import dorkbox.systemTray.Menu;
import dorkbox.systemTray.MenuItem;
import io.github.aleksavelickovic.service.ProcessService;
import io.github.aleksavelickovic.ui.MainWindow;
import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrayManager {

    private final ProcessService processService; // Dependecy Injection

    private final String osName = System.getProperty("os.name");

    public TrayManager(MainWindow window, ProcessService processService) {
        this.processService = processService;

        dorkbox.systemTray.SystemTray systemTray = dorkbox.systemTray.SystemTray.get();
        if (systemTray == null) {
            return; // System tray is not supported on the user's platform
        }


        Image image = Toolkit.getDefaultToolkit().getImage(
                getClass().getResource("/images/logo.png")
        );

        systemTray.setImage(image);
        systemTray.setStatus("Dual Boot Manager");

        Menu menu = systemTray.getMenu();

        // TODO Options for reboot

        for (String option : window.options) {
            menu.add(new MenuItem(option, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        if (!option.equals(osName)) {
                            Process process1 = processService.execute(ProcessService.SET_GRUB, true, "\"" + option + "\"");

                            while (process1.isAlive()) { // TODO
                                Thread.sleep(500);
                            }
                        }
                        Process process2 = processService.execute(ProcessService.REBOOT, false);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }));
        }

        menu.add(new MenuItem("Open Main Window", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setVisible(true);
                window.setExtendedState(JFrame.NORMAL);
            }
        }));

        menu.add(new MenuItem("Quit", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                systemTray.shutdown();
                System.exit(0);
            }
        }));
    }

}
