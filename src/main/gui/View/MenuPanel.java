package main.gui.View;

import main.gui.dataTypes.Settings;

import javax.swing.*;

public class MenuPanel extends JMenuBar {
    public JMenuItem getSaveMenu() {
        return saveMenu;
    }

    public JMenuItem getLoadMenu() {
        return loadMenu;
    }

    public JRadioButtonMenuItem getResetOn3InvalidLogin() {
        return resetOn3InvalidLogin;
    }

    public JMenuItem getResetMenu() {
        return resetMenu;
    }

    final JMenuItem saveMenu = new JMenuItem("Save");
    final JMenuItem loadMenu = new JMenuItem("Load");
    final JRadioButtonMenuItem resetOn3InvalidLogin = new JRadioButtonMenuItem("Factory reset after 3 invalid login");
    final JMenuItem resetMenu = new JMenuItem("Factory reset");

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
