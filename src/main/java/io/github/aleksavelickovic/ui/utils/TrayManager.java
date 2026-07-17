package io.github.aleksavelickovic.ui.utils;

import dorkbox.systemTray.Menu;
import dorkbox.systemTray.MenuItem;
import io.github.aleksavelickovic.ui.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrayManager {

    public TrayManager(MainWindow window) {

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
                    System.out.println("Option pressed: " + option); // TODO implement logic trough ProcessService
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
