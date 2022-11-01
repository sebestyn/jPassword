package gui;

import dataTypes.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuPanel extends JMenuBar {
    JMenuItem saveMenu = new JMenuItem("Save");
    JMenuItem loadMenu = new JMenuItem("Load");
    JRadioButtonMenuItem resetOn3InvalidLogin = new JRadioButtonMenuItem("Factory reset after 3 invalid login");
    JMenuItem resetMenu = new JMenuItem("Factory reset");

    public MenuPanel(){

        // File
        JMenu FileMenu = new JMenu("File");
        FileMenu.add(saveMenu);
        FileMenu.add(loadMenu);

        // Settings
        JMenu settingsMenu = new JMenu("Settings");
        settingsMenu.add(resetOn3InvalidLogin);
        settingsMenu.add(resetMenu);

        this.add(FileMenu);
        this.add(settingsMenu);
    }

    public void init(Settings settings){
        resetOn3InvalidLogin.setSelected(settings.getFactoryReset());
    }

}
