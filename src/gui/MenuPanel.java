package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuPanel extends JMenuBar {
    public MenuPanel(){

        // File
        JMenu FileMenu = new JMenu("File");
        JMenuItem saveMenu = new JMenuItem("Save");
        JMenuItem loadMenu = new JMenuItem("Load");
        FileMenu.add(saveMenu);
        FileMenu.add(loadMenu);

        // Settings
        JMenu settingsMenu = new JMenu("Settings");
        JRadioButtonMenuItem resetOn3InvalidLogin = new JRadioButtonMenuItem("Reset in 3 invalid login attemps");
        JMenuItem resetMenu = new JMenuItem("Reset now");
        settingsMenu.add(resetOn3InvalidLogin);
        settingsMenu.add(resetMenu);

        this.add(FileMenu);
        this.add(settingsMenu);
    }

}
